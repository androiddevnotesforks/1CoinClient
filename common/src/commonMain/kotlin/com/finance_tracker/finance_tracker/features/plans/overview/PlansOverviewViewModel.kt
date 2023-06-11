package com.finance_tracker.finance_tracker.features.plans.overview

import com.finance_tracker.finance_tracker.core.common.date.currentYearMonth
import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.plans.MonthExpenseLimitInteractor
import com.finance_tracker.finance_tracker.domain.interactors.plans.PlansInteractor
import com.finance_tracker.finance_tracker.domain.models.MonthExpenseLimitChartData
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.finance_tracker.finance_tracker.features.plans.overview.analytics.PlansOverviewAnalytics
import kotlinx.datetime.Clock

class PlansOverviewViewModel(
    private val plansOverviewAnalytics: PlansOverviewAnalytics,
    monthExpenseLimitInteractor: MonthExpenseLimitInteractor,
    plansInteractor: PlansInteractor
): BaseViewModel<PlansOverviewAction>() {

    private val currentYearMonth = Clock.System.currentYearMonth()

    val monthExpenseLimitChartData = monthExpenseLimitInteractor.getMonthExpenseLimitChartData(currentYearMonth)
        .stateIn(viewModelScope, initialValue = MonthExpenseLimitChartData.Empty)

    val plans = plansInteractor.getPlans(currentYearMonth)
        .stateIn(viewModelScope, initialValue = emptyList())

    fun onAddCategoryExpenseLimitClick() {
        plansOverviewAnalytics.trackAddLimitClick()
        viewAction = PlansOverviewAction.OpenSetupPlanScreen
    }

    fun onCategoryExpenseLimitClick(plan: Plan) {
        plansOverviewAnalytics.trackLimitClick(plan)
        viewAction = PlansOverviewAction.OpenEditPlanScreen(plan)
    }
}
