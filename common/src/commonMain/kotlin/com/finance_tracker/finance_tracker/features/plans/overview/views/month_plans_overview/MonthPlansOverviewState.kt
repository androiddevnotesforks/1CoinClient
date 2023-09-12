package com.finance_tracker.finance_tracker.features.plans.overview.views.month_plans_overview

import com.finance_tracker.finance_tracker.domain.models.MonthExpenseLimitChartData
import com.finance_tracker.finance_tracker.domain.models.Plan

data class MonthPlansOverviewState(
    val plans: List<Plan> = emptyList(),
    val monthExpenseLimitChartData: MonthExpenseLimitChartData = MonthExpenseLimitChartData.Empty
)
