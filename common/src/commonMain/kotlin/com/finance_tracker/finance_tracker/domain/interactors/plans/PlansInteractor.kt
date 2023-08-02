package com.finance_tracker.finance_tracker.domain.interactors.plans

import com.finance_tracker.finance_tracker.core.common.convertToCurrencyAmount
import com.finance_tracker.finance_tracker.core.common.convertToCurrencyValue
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.data.repositories.PlansRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class PlansInteractor(
    private val plansRepository: PlansRepository,
    private val currenciesRepository: CurrenciesRepository,
    private val transactionsRepository: TransactionsRepository
) {

    suspend fun addPlan(
        yearMonth: YearMonth,
        plan: Plan
    ) {
        plansRepository.addOrUpdatePlan(yearMonth, plan)
    }

    suspend fun updatePlan(
        yearMonth: YearMonth,
        oldPlan: Plan,
        newPlan: Plan
    ) {
        val plan = if (oldPlan.spentAmount != newPlan.limitAmount) {
            val currencyRates = currenciesRepository.getCurrencyRates()
            val toCurrency = currenciesRepository.getPrimaryCurrency()
            newPlan.copy(
                spentAmount = oldPlan.spentAmount.convertToCurrencyAmount(toCurrency, currencyRates),
            )
        } else {
            newPlan
        }
        plansRepository.addOrUpdatePlan(yearMonth, plan)
    }

    suspend fun deletePlan(plan: Plan) {
        plansRepository.deletePlan(plan)
    }

    fun getPlans(yearMonth: YearMonth): Flow<List<Plan>> {
        return combine(
            plansRepository.getPlans(yearMonth),
            currenciesRepository.getCurrencyRatesFlow(),
            currenciesRepository.getPrimaryCurrencyFlow(),
            transactionsRepository.getTransactionsFlow(
                transactionType = TransactionType.Expense,
                yearMonth = yearMonth
            )
        ) { plans, currencyRates, primaryCurrency, transactions ->
            plans.map { plan ->
                plan.copy(
                    spentAmount = getSpentAmount(
                        plan = plan,
                        transactions = transactions,
                        currencyRates = currencyRates,
                        primaryCurrency = primaryCurrency
                    ),
                    limitAmount = plan.limitAmount.convertToCurrencyAmount(
                        currencyRates = currencyRates,
                        toCurrency = primaryCurrency
                    )
                )
            }
        }
    }

    private fun getSpentAmount(
        plan: Plan,
        transactions: List<Transaction>,
        currencyRates: CurrencyRates,
        primaryCurrency: Currency
    ): Amount {
        val spentAmountValue = transactions
            .filter { plan.category == it._category }
            .sumOf {
                it.primaryAmount.convertToCurrencyValue(
                    toCurrency = primaryCurrency,
                    currencyRates = currencyRates
                )
            }

        return Amount(
            currency = primaryCurrency,
            amountValue = spentAmountValue
        )
    }

    fun getMonthLimitAmount(yearMonth: YearMonth): Flow<Amount> {
        val currencyFlow = currenciesRepository.getPrimaryCurrencyFlow()
        val currencyRatesFlow = currenciesRepository.getCurrencyRatesFlow()
        val monthLimitFlow = plansRepository.getMonthLimitAmount(yearMonth)
        return combine(currencyFlow, currencyRatesFlow, monthLimitFlow) {
                currency, currencyRates, monthLimit ->
            monthLimit.convertToCurrencyAmount(
                toCurrency = currency,
                currencyRates = currencyRates
            )
        }
    }

    suspend fun setMonthLimit(
        yearMonth: YearMonth,
        limit: Amount
    ) {
        plansRepository.setMonthLimit(yearMonth, limit)
    }
}