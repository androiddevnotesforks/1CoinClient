package com.finance_tracker.finance_tracker.features.plans.overview

import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.PlansInteractor
import com.finance_tracker.finance_tracker.features.plans.overview.analytics.PlansOverviewAnalytics

class PlansOverviewViewModel(
    private val plansOverviewAnalytics: PlansOverviewAnalytics,
    plansInteractor: PlansInteractor
): BaseViewModel<PlansOverviewAction>() {

    val plans = plansInteractor.getPlans()
        .stateIn(viewModelScope, initialValue = emptyList())

    fun onAddLimitClick() {
        plansOverviewAnalytics.trackAddLimitClick()
        viewAction = PlansOverviewAction.OpenSetupPlanScreen
    }
}
