package com.finance_tracker.finance_tracker.features.plans.overview

sealed interface PlansOverviewViewAction {
    data class ScrollToPage(
        val page: Int
    ): PlansOverviewViewAction
}