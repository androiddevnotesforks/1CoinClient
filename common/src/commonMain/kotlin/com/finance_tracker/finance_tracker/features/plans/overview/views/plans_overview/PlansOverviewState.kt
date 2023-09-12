package com.finance_tracker.finance_tracker.features.plans.overview.views.plans_overview

import com.finance_tracker.finance_tracker.features.plans.overview.views.plan_period.PlanPeriodState

data class PlansOverviewState(
    val planPeriodState: PlanPeriodState = PlanPeriodState.Empty
) {
    companion object {
        const val MaxMonths = 13
        const val InitialMonthIndex = MaxMonths - 2
    }
}
