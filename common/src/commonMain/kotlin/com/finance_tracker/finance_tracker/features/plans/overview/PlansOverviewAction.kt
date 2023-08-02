package com.finance_tracker.finance_tracker.features.plans.overview

import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.domain.models.Plan

sealed interface PlansOverviewAction {

    data class OpenSetupPlanScreen(
        val yearMonth: YearMonth
    ): PlansOverviewAction

    data class OpenEditPlanScreen(
        val yearMonth: YearMonth,
        val plan: Plan
    ): PlansOverviewAction

    data class ScrollToPage(
        val page: Int
    ): PlansOverviewAction

    data class OpenSetLimitDialog(
        val yearMonth: YearMonth
    ): PlansOverviewAction
}