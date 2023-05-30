package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.data.repositories.PlansRepository
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.finance_tracker.finance_tracker.domain.models.convertToCurrencyAmount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class PlansInteractor(
    private val plansRepository: PlansRepository,
    private val currenciesRepository: CurrenciesRepository
) {

    suspend fun addPlan(plan: Plan) {
        plansRepository.addOrUpdatePlan(plan)
    }

    suspend fun updatePlan(oldPlan: Plan, newPlan: Plan) {
        val plan = if (oldPlan.spentAmount != newPlan.limitAmount) {
            val currencyRates = currenciesRepository.getCurrencyRates()
            val toCurrency = currenciesRepository.getPrimaryCurrency()
            newPlan.copy(
                spentAmount = oldPlan.spentAmount.convertToCurrencyAmount(currencyRates, toCurrency),
            )
        } else {
            newPlan
        }
        plansRepository.addOrUpdatePlan(plan)
    }

    suspend fun deletePlan(plan: Plan) {
        plansRepository.deletePlan(plan)
    }

    fun getPlans(): Flow<List<Plan>> {
        return combine(
            plansRepository.getPlans(),
            currenciesRepository.getCurrencyRatesFlow(),
            currenciesRepository.getPrimaryCurrencyFlow()
        ) { plans, currencyRates, primaryCurrency ->
            plans.map {
                it.copy(
                    spentAmount = it.spentAmount.convertToCurrencyAmount(
                        currencyRates = currencyRates,
                        toCurrency = primaryCurrency
                    ),
                    limitAmount = it.limitAmount.convertToCurrencyAmount(
                        currencyRates = currencyRates,
                        toCurrency = primaryCurrency
                    )
                )
            }
        }
    }
}