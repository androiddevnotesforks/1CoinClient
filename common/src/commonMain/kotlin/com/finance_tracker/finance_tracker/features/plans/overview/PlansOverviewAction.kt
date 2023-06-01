package com.finance_tracker.finance_tracker.features.plans.overview

import com.finance_tracker.finance_tracker.domain.models.Plan

sealed interface PlansOverviewAction {

    object OpenSetupPlanScreen: PlansOverviewAction

    data class OpenEditPlanScreen(
        val plan: Plan
    ): PlansOverviewAction
}