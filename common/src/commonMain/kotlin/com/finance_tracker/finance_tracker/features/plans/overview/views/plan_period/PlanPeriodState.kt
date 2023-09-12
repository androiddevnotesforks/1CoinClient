package com.finance_tracker.finance_tracker.features.plans.overview.views.plan_period

import com.finance_tracker.finance_tracker.core.common.date.currentYearMonth
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import kotlinx.datetime.Clock

data class PlanPeriodState(
    val selectedPeriod: YearMonth,
    val isNextPeriodEnabled: Boolean,
    val isPreviousPeriodEnabled: Boolean
) {
    companion object {
        val Empty = PlanPeriodState(
            selectedPeriod = Clock.System.currentYearMonth(),
            isNextPeriodEnabled = false,
            isPreviousPeriodEnabled = false
        )
    }
}