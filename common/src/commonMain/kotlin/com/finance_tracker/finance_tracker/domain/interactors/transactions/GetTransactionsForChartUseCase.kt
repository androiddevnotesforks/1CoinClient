package com.finance_tracker.finance_tracker.domain.interactors.transactions

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.convertToCurrencyValue
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.toLimitedFloat
import com.finance_tracker.finance_tracker.core.common.toString
import com.finance_tracker.finance_tracker.core.theme.ChartConfig
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetTransactionsForChartUseCase(
    private val transactionsRepository: TransactionsRepository
) {
    @Suppress("MagicNumber")
    suspend fun invoke(
        transactionType: TransactionType,
        yearMonth: YearMonth,
        primaryCurrency: Currency,
        currencyRates: CurrencyRates
    ): TxsByCategoryChart {
        return withContext(Dispatchers.IO) {
            val transactions = transactionsRepository.getTransactions(transactionType, yearMonth)
            val totalAmount = transactions.sumOf {
                it.primaryAmount.convertToCurrencyValue(primaryCurrency, currencyRates)
            }

            val sortRules: (TxsByCategoryChart.Piece) -> Double = {
                it.amount.convertToCurrencyValue(primaryCurrency, currencyRates)
            }
            val rawCategoryPieces = transactions
                .groupBy { it._category }
                .map { (category, transactions) ->
                    TxsByCategoryChart.Piece(
                        category = category,
                        amount = Amount(
                            currency = primaryCurrency,
                            amountValue = transactions.sumOf {
                                it.primaryAmount.convertToCurrencyValue(primaryCurrency, currencyRates)
                            }
                        ),
                        percentValue = 0f,
                        transactionsCount = transactions.size
                    )
                }.sortedByDescending { sortRules(it) }

            val categoryPercentValues = calculateCategoryPercentValues(
                rawCategoryPieces, totalAmount
            )
            val allPieces = rawCategoryPieces.mapIndexed { index, piece ->
                piece.copy(
                    percentValue = categoryPercentValues[index].toLimitedFloat()
                )
            }

            val otherPieces = (allPieces.drop(OtherCategoryCountThreshold) +
                    allPieces.filter { it.percentValue < OtherMinPercentThreshold }).toSet()
                .sortedByDescending { sortRules(it) }
                .takeIf { it.size > 1 }
                .orEmpty()

            allPieces.forEachIndexed { index, piece ->
                piece.color = if (piece in otherPieces) {
                    ChartConfig.colorOther
                } else {
                    ChartConfig.colors[index % ChartConfig.colors.size]
                }
            }

            val mainPieces = if (otherPieces.isNotEmpty()) {
                allPieces - otherPieces.toSet() + TxsByCategoryChart.Piece(
                    category = Category(
                        id = -2,
                        name = "Other",
                        icon = MR.images.ic_more_horiz,
                        isExpense = true,
                        isIncome = true
                    ),
                    amount = Amount(
                        currency = primaryCurrency,
                        amountValue = otherPieces.sumOf {
                            it.amount.convertToCurrencyValue(primaryCurrency, currencyRates)
                        }
                    ),
                    percentValue = otherPieces.sumOf {
                        it.percentValue.toDouble()
                    }.toLimitedFloat(),
                    transactionsCount = otherPieces.sumOf { it.transactionsCount }
                ).apply {
                    color = ChartConfig.colorOther
                }
            } else {
                allPieces
            }

            return@withContext TxsByCategoryChart(
                mainPieces = mainPieces,
                allPieces = allPieces,
                total = Amount(
                    currency = primaryCurrency,
                    amountValue = totalAmount
                )
            )
        }
    }

    private fun calculateCategoryPercentValues(
        allCategoryPieces: List<TxsByCategoryChart.Piece>,
        totalAmount: Double
    ): List<Double> {
        var accumulatedTotalPercent = 0.0
        val reversedCategoryPercentValues = mutableListOf<Double>()
        for (index in allCategoryPieces.size - 1 downTo  0) {
            val piece = allCategoryPieces[index]
            if (index == 0) {
                reversedCategoryPercentValues.add(100.0 - accumulatedTotalPercent)
            } else {
                val percentValue = (piece.amount.amountValue / totalAmount * 100).toString(
                    TxsByCategoryChart.Piece.PERCENT_PRECISION
                ).toDouble()
                accumulatedTotalPercent += percentValue
                reversedCategoryPercentValues.add(percentValue)
            }
        }
        return reversedCategoryPercentValues.reversed()
    }

    companion object {
        private const val OtherMinPercentThreshold = 1
        private val OtherCategoryCountThreshold = ChartConfig.colors.size
    }
}