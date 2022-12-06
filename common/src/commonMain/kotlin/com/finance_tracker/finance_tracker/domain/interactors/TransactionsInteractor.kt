package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.core.theme.ChartConfig
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import io.github.koalaplot.core.util.toString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Month
import java.util.Calendar
import java.util.Date

class TransactionsInteractor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
) {

    suspend fun getTransactions(accountId: Long? = null): List<TransactionListModel> {
        val allTransactions = if (accountId == null) {
            transactionsRepository.getAllTransactions()
        } else {
            transactionsRepository.getTransactions(accountId)
        }
        val newTransactions = mutableListOf<TransactionListModel>()
        for (transaction in allTransactions) {
            val lastUiTransactionModel = newTransactions.lastOrNull()

            val isTransactionForNextDay = lastUiTransactionModel is TransactionListModel.Data &&
                    !lastUiTransactionModel.transaction.date.isCalendarDateEquals(transaction.date)
            if (lastUiTransactionModel == null || isTransactionForNextDay) {
                newTransactions += TransactionListModel.DateAndDayTotal(
                    date = transaction.date
                )
            }
            newTransactions += TransactionListModel.Data(transaction)
        }
        return newTransactions
    }

    private suspend fun updateAccountBalanceForDeleteTransaction(transaction: Transaction) {
        if (transaction.type == TransactionType.Expense) {
            accountsRepository.increaseAccountBalance(transaction.account.id, transaction.amount.amountValue)
        } else {
            accountsRepository.reduceAccountBalance(transaction.account.id, transaction.amount.amountValue)
        }
    }

    private suspend fun updateAccountBalanceForAddTransaction(transaction: Transaction) {
        if (transaction.type == TransactionType.Expense) {
            accountsRepository.reduceAccountBalance(transaction.account.id, transaction.amount.amountValue)
        } else {
            accountsRepository.increaseAccountBalance(transaction.account.id, transaction.amount.amountValue)
        }
    }

    suspend fun deleteTransactions(transactions: List<Transaction>) {
        transactionsRepository.deleteTransactions(transactions)

        transactions.forEach {
            updateAccountBalanceForDeleteTransaction(it)
        }
    }

    suspend fun addOrUpdateTransaction(transaction: Transaction) {
        transactionsRepository.addOrUpdateTransaction(transaction)
        updateAccountBalanceForAddTransaction(transaction)
    }

    private fun Date?.isCalendarDateEquals(date: Date?): Boolean {
        if (this == date) return true
        if (this == null) return false
        if (date == null) return false

        val calendar1 = Calendar.getInstance().apply { time = this@isCalendarDateEquals }
        val calendar2 = Calendar.getInstance().apply { time = date }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }

    @Suppress("MagicNumber")
    suspend fun getTransactions(
        transactionType: TransactionType,
        month: Month,
        primaryCurrency: Currency,
        currencyRates: CurrencyRates
    ): TxsByCategoryChart {
        return withContext(Dispatchers.Default) {
            val transactions = transactionsRepository.getTransactions(transactionType, month)
            val totalAmount = transactions.sumOf {
                it.amount.convertToCurrency(currencyRates, primaryCurrency)
            }

            val sortRules: (TxsByCategoryChart.Piece) -> Double = {
                it.amount.convertToCurrency(currencyRates, primaryCurrency)
            }
            val rawCategoryPieces = transactions
                .groupBy { it.category }
                .map { (category, transactions) ->
                    TxsByCategoryChart.Piece(
                        category = category ?: Category.EMPTY,
                        amount = Amount(
                            currency = primaryCurrency,
                            amountValue = transactions.sumOf {
                                it.amount.convertToCurrency(currencyRates, primaryCurrency)
                            }
                        ),
                        percentValue = 0f,
                        transactionsCount = transactions.size
                    )
                }.sortedByDescending { sortRules.invoke(it) }

            val categoryPercentValues = calculateCategoryPercentValues(
                rawCategoryPieces, totalAmount
            )
            val allPieces = rawCategoryPieces.mapIndexed { index, piece ->
                piece.copy(
                    percentValue = categoryPercentValues[index].toFloat()
                )
            }

            val otherPieces = (allPieces.drop(OtherCategoryCountThreshold) +
                    allPieces.filter { it.percentValue < OtherMinPercentThreshold }).toSet()
                .sortedByDescending { sortRules.invoke(it) }
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
                        iconId = "ic_more_horiz"
                    ),
                    amount = Amount(
                        currency = primaryCurrency,
                        amountValue = otherPieces.sumOf {
                            it.amount.convertToCurrency(currencyRates, primaryCurrency)
                        }
                    ),
                    percentValue = otherPieces.sumOf {
                        it.percentValue.toDouble()
                    }.toFloat(),
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