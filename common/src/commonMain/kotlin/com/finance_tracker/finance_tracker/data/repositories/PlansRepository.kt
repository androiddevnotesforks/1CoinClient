package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.database.mappers.EmptyCategoryId
import com.finance_tracker.finance_tracker.data.database.mappers.fullPlanMapper
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.financetracker.financetracker.data.LimitsEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PlansRepository(
    private val limitsEntityQueries: LimitsEntityQueries
) {

    suspend fun addOrUpdatePlan(plan: Plan) {
        withContext(Dispatchers.Default) {
            limitsEntityQueries.insertLimit(
                id = plan.id,
                categoryId = plan.category.id,
                limitAmount = plan.limitAmount.amountValue,
                currency = plan.limitAmount.currency.code
            )
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
    fun getPlans(): Flow<List<Plan>> {
        return limitsEntityQueries.getAllFullLimits(
            mapper = fullPlanMapper
        )
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { plans ->
                plans.filter { it.category.id != EmptyCategoryId }
            }
    }
}