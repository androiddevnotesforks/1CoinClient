package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.data.database.mappers.EmptyCategoryId
import com.finance_tracker.finance_tracker.data.database.mappers.fullPlanMapper
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.financetracker.financetracker.data.LimitsEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.number

class PlansRepository(
    private val limitsEntityQueries: LimitsEntityQueries
) {

    suspend fun addOrUpdatePlan(
        yearMonth: YearMonth,
        plan: Plan
    ) {
        withContext(Dispatchers.Default) {
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

        withContext(Dispatchers.Default) {
            limitsEntityQueries.deleteLimitById(id = plan.id)
        }
    }

    suspend fun deletePlansByCategoryId(categoryId: Long?) {
        withContext(Dispatchers.Default) {
            limitsEntityQueries.deleteLimitsByCategoryId(categoryId)
        }
    }

    @Suppress("UnusedPrivateMember")
    fun getPlans(yearMonth: YearMonth): Flow<List<Plan>> {
        return limitsEntityQueries.getAllFullLimits(
            year = yearMonth.year.toLong(),
            month = yearMonth.month.number.toLong(),
            mapper = fullPlanMapper
        )
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { plans ->
                plans.filter { it.category.id != EmptyCategoryId }
            }
    }

    @Suppress("UnusedPrivateMember")
    fun getMonthLimitAmount(yearMonth: YearMonth): Flow<Amount> {
        // TODO: Get real limit amount from local storage
        return flowOf(
            Amount(
                currency = Currency.default,
                amountValue = 100.0
            )
        )
    }
}