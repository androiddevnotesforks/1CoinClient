package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.data.database.mappers.EmptyCategoryId
import com.finance_tracker.finance_tracker.data.database.mappers.fullPlanMapper
import com.finance_tracker.finance_tracker.data.settings.AccountSettings
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.financetracker.financetracker.data.LimitsEntityQueries
import com.financetracker.financetracker.data.TotalMonthLimitEntity
import com.financetracker.financetracker.data.TotalMonthLimitEntityQueries
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.number

class PlansRepository(
    private val limitsEntityQueries: LimitsEntityQueries,
    private val totalMonthLimitEntityQueries: TotalMonthLimitEntityQueries,
    private val accountSettings: AccountSettings
) {

    suspend fun addOrUpdatePlan(
        yearMonth: YearMonth,
        plan: Plan
    ) {
        withContext(Dispatchers.IO) {
            limitsEntityQueries.transaction {
                val isPlanExist = limitsEntityQueries
                    .getFullLimitByCategoryId(
                        categoryId = plan.category.id,
                        year = yearMonth.year.toLong(),
                        month = yearMonth.month.number.toLong()
                    )
                    .executeAsOneOrNull() != null

                if (isPlanExist) {
                    limitsEntityQueries.updateLimitByCategoryId(
                        limitAmount = plan.limitAmount.amountValue,
                        currency = plan.limitAmount.currency.code,
                        categoryId = plan.category.id,
                        year = yearMonth.year.toLong(),
                        month = yearMonth.month.number.toLong()
                    )
                } else {
                    limitsEntityQueries.insertLimit(
                        id = plan.id,
                        categoryId = plan.category.id,
                        limitAmount = plan.limitAmount.amountValue,
                        currency = plan.limitAmount.currency.code,
                        year = yearMonth.year.toLong(),
                        month = yearMonth.month.number.toLong()
                    )
                }
            }
        }
    }

    suspend fun deletePlan(plan: Plan) {
        if (plan.id == null) return

        withContext(Dispatchers.IO) {
            limitsEntityQueries.deleteLimitById(id = plan.id)
        }
    }

    suspend fun deletePlansByCategoryId(categoryId: Long?) {
        withContext(Dispatchers.IO) {
            limitsEntityQueries.deleteLimitsByCategoryId(categoryId)
        }
    }

    fun getPlans(yearMonth: YearMonth): Flow<List<Plan>> {
        return limitsEntityQueries.getAllFullLimits(
            year = yearMonth.year.toLong(),
            month = yearMonth.month.number.toLong(),
            mapper = fullPlanMapper
        )
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { plans ->
                plans.filter { it.category.id != EmptyCategoryId }
            }
    }

    fun getMonthLimitAmount(yearMonth: YearMonth): Flow<Amount> {
        return combine(getDefaultAmountFlow(), getMonthLimitFlow(yearMonth)) { defaultAmount, monthLimit ->
            val monthLimitEntity = monthLimit.executeAsOneOrNull() ?: return@combine defaultAmount
            Amount(
                currency = mapCodeToCurrency(monthLimitEntity.currency),
                amountValue = monthLimitEntity.limitAmount
            )
        }
            .flowOn(Dispatchers.IO)
    }

    private fun getMonthLimitFlow(yearMonth: YearMonth): Flow<Query<TotalMonthLimitEntity>> {
        return totalMonthLimitEntityQueries.getMonthLimit(
            year = yearMonth.year.toLong(),
            month = yearMonth.month.number.toLong()
        ).asFlow()
    }

    private fun getDefaultAmountFlow(): Flow<Amount> {
        return getPrimaryCurrencyFlow()
            .map {
                Amount(
                    currency = it,
                    amountValue = 0.0
                )
            }
    }

    private fun getPrimaryCurrencyFlow(): Flow<Currency> {
        return accountSettings.getPrimaryCurrencyCodeFlow()
            .map(::mapCodeToCurrency)
    }

    private fun mapCodeToCurrency(currencyCode: String?): Currency {
        return currencyCode?.let(Currency::getByCode) ?: Currency.default
    }

    @Suppress("UnusedPrivateMember")
    suspend fun setMonthLimit(yearMonth: YearMonth, limit: Amount) {
        withContext(Dispatchers.IO) {
            totalMonthLimitEntityQueries.insertMonthLimit(
                year = yearMonth.year.toLong(),
                month = yearMonth.month.number.toLong(),
                limitAmount = limit.amountValue,
                currency = limit.currency.code
            )
        }
    }
}