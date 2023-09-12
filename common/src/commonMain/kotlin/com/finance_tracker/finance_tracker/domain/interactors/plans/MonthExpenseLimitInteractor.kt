package com.finance_tracker.finance_tracker.domain.interactors.plans

import com.finance_tracker.finance_tracker.core.common.convertToCurrencyAmount
import com.finance_tracker.finance_tracker.core.common.convertToCurrencyAmountTransaction
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.minus
import com.finance_tracker.finance_tracker.core.common.sumOf
import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.data.repositories.PlansRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.MonthExpenseLimitChartData
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class MonthExpenseLimitInteractor(
    private val plansRepository: PlansRepository,
    private val currenciesRepository: CurrenciesRepository,
    private val transactionsRepository: TransactionsRepository
) {

    fun getMonthExpenseLimitChartData(yearMonth: YearMonth): Flow<MonthExpenseLimitChartData> {
        return combine(
            getMonthSpentAmount(yearMonth),
            getMonthLimitAmount(yearMonth)
        ) { monthSpentAmount, monthLimitAmount ->
            MonthExpenseLimitChartData(
                pieces = calculatePieChartPieces(
                    monthSpentAmount, monthLimitAmount
                ),
                spent = monthSpentAmount,
                totalLimit = monthLimitAmount
            )
        }
    }

    private fun getMonthSpentAmount(yearMonth: YearMonth): Flow<Amount> {
        return combine(
            currenciesRepository.getPrimaryCurrencyFlow(),
            currenciesRepository.getCurrencyRatesFlow(),
            transactionsRepository.getTransactionsFlow(
                transactionType = TransactionType.Expense,
                yearMonth = yearMonth
            )
        ) { primaryCurrency, currencyRates, transactions ->
            transactions
                .filter { it.type == TransactionType.Expense }
                .map {
                    it.convertToCurrencyAmountTransaction(
                        toCurrency = primaryCurrency,
                        currencyRates = currencyRates
                    )
                }
                .sumOf(currency = primaryCurrency) { it.primaryAmount }
        }
    }

    private fun getMonthLimitAmount(yearMonth: YearMonth): Flow<Amount> {
        return combine(
            currenciesRepository.getPrimaryCurrencyFlow(),
            currenciesRepository.getCurrencyRatesFlow(),
            plansRepository.getMonthLimitAmount(yearMonth)
        ) { primaryCurrency, currencyRates, limitAmount ->
            limitAmount.convertToCurrencyAmount(
                toCurrency = primaryCurrency,
                currencyRates = currencyRates
            )
        }
    }

    @Suppress("UnnecessaryParentheses")
    private fun calculatePieChartPieces(
        monthSpentAmount: Amount,
        monthLimitAmount: Amount
    ): List<MonthExpenseLimitChartData.Piece> {
        return when {
            monthLimitAmount.amountValue <= 0.0 -> {
                listOf(
                    MonthExpenseLimitChartData.Piece(
                        type = MonthExpenseLimitChartData.MonthExpenseLimitType.PlannedSpent,
                        showLabel = false,
                        amount = Amount.default(monthSpentAmount.currency),
                        percent = 0.toFloat()
                    ),
                    MonthExpenseLimitChartData.Piece(
                        type = MonthExpenseLimitChartData.MonthExpenseLimitType.Remain,
                        showLabel = false,
                        amount = Amount.default(monthLimitAmount.currency),
                        percent = 100.toFloat()
                    )
                )
            }
            monthSpentAmount.amountValue <= monthLimitAmount.amountValue -> {
                val spentPercent = monthSpentAmount.amountValue / monthLimitAmount.amountValue * 100.0
                val remainPercent = 100.0 - spentPercent
                val remainAmount = monthLimitAmount - monthSpentAmount
                listOf(
                    MonthExpenseLimitChartData.Piece(
                        type = MonthExpenseLimitChartData.MonthExpenseLimitType.PlannedSpent,
                        showLabel = true,
                        amount = monthSpentAmount,
                        percent = spentPercent.toFloat()
                    ),
                    MonthExpenseLimitChartData.Piece(
                        type = MonthExpenseLimitChartData.MonthExpenseLimitType.Remain,
                        showLabel = true,
                        amount = remainAmount,
                        percent = remainPercent.toFloat()
                    )
                )
            }
            else -> {
                val overSpentAmount = monthSpentAmount - monthLimitAmount
                val overSpentPercent = (monthSpentAmount.amountValue / monthLimitAmount.amountValue - 1.0)
                    .coerceIn(0.0, 1.0) * 100.0
                val plannedSpentPercent = 100.0 - overSpentPercent
                val percentValue = (monthSpentAmount.amountValue / monthLimitAmount.amountValue) * 100.0

                listOf(
                    MonthExpenseLimitChartData.Piece(
                        type = MonthExpenseLimitChartData.MonthExpenseLimitType.OverSpent,
                        showLabel = true,
                        amount = overSpentAmount,
                        percent = overSpentPercent.toFloat(),
                        percentInLabel = percentValue.toFloat()
                    ),
                    MonthExpenseLimitChartData.Piece(
                        type = MonthExpenseLimitChartData.MonthExpenseLimitType.PlannedSpent,
                        showLabel = false,
                        amount = monthSpentAmount,
                        percent = plannedSpentPercent.toFloat(),
                        percentInLabel = 0f
                    )
                )
            }
        }
    }
}