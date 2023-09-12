package com.finance_tracker.finance_tracker.features.plans.overview

import com.finance_tracker.finance_tracker.core.common.date.currentYearMonth
import com.finance_tracker.finance_tracker.core.common.date.plusMonth
import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.plans.MonthExpenseLimitInteractor
import com.finance_tracker.finance_tracker.domain.interactors.plans.PlansInteractor
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.finance_tracker.finance_tracker.features.plans.overview.analytics.PlansOverviewAnalytics
import com.finance_tracker.finance_tracker.features.plans.overview.views.month_plans_overview.MonthPlansOverviewState
import com.finance_tracker.finance_tracker.features.plans.overview.views.plan_period.PlanPeriodState
import com.finance_tracker.finance_tracker.features.plans.overview.views.plans_overview.PlansOverviewCallback
import com.finance_tracker.finance_tracker.features.plans.overview.views.plans_overview.PlansOverviewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class PlansOverviewViewModel(
    private val plansOverviewAnalytics: PlansOverviewAnalytics,
    private val monthExpenseLimitInteractor: MonthExpenseLimitInteractor,
    private val plansInteractor: PlansInteractor
): BaseViewModel<PlansOverviewAction>(), PlansOverviewCallback {

    private val currentYearMonth = Clock.System.currentYearMonth()
    private val selectedYearMonth = MutableStateFlow(currentYearMonth)
    private val planPeriodState = selectedYearMonth.map { yearMonth ->
        PlanPeriodState(
            selectedPeriod = yearMonth,
            isNextPeriodEnabled = yearMonth != months.last(),
            isPreviousPeriodEnabled = yearMonth != months.first()
        )
    }.stateIn(viewModelScope, initialValue = PlanPeriodState.Empty)

    val plansOverviewState = planPeriodState
        .map { planPeriodState ->
            PlansOverviewState(
                planPeriodState = planPeriodState
            )
        }
        .stateIn(viewModelScope, initialValue = PlansOverviewState())
    private val months = List(PlansOverviewState.MaxMonths) {
        currentYearMonth.plusMonth(it - 11)
    }

    fun onAddCategoryExpenseLimitClick() {
        plansOverviewAnalytics.trackAddLimitClick()
        viewAction = PlansOverviewAction.OpenSetupPlanScreen(
            yearMonth = selectedYearMonth.value,
        )
    }

    fun onCategoryExpenseLimitClick(plan: Plan) {
        plansOverviewAnalytics.trackLimitClick(plan)
        viewAction = PlansOverviewAction.OpenEditPlanScreen(
            yearMonth = selectedYearMonth.value,
            plan = plan
        )
    }

    override fun onPageChange(page: Int) {
        selectedYearMonth.value = months[page]
    }

    override fun onPreviousPeriodClick() {
        plansOverviewAnalytics.trackPreviousPeriodClick(selectedYearMonth.value)
        val previousMonthIndex = months.indexOf(selectedYearMonth.value) - 1
        viewAction = PlansOverviewAction.ScrollToPage(previousMonthIndex)
    }

    override fun onNextPeriodClick() {
        plansOverviewAnalytics.trackNextPeriodClick(selectedYearMonth.value)
        val nextMonthIndex = months.indexOf(selectedYearMonth.value) + 1
        viewAction = PlansOverviewAction.ScrollToPage(nextMonthIndex)
    }

    fun getState(page: Int): StateFlow<MonthPlansOverviewState> {
        val yearMonth = months[page]

        val monthExpenseLimitChartDataFlow = monthExpenseLimitInteractor.getMonthExpenseLimitChartData(yearMonth)
        val plansFlow = plansInteractor.getPlans(yearMonth)

        return combine(plansFlow , monthExpenseLimitChartDataFlow) {
                plans , monthExpenseLimitChartData ->
            MonthPlansOverviewState(
                plans = plans,
                monthExpenseLimitChartData = monthExpenseLimitChartData
            )
        }
            .stateIn(viewModelScope, initialValue = MonthPlansOverviewState())
    }

    override fun onSetLimitClick() {
        val selectedYearMonth = selectedYearMonth.value
        plansOverviewAnalytics.trackSetLimitClick(selectedYearMonth)
        viewAction = PlansOverviewAction.OpenSetLimitDialog(selectedYearMonth)
    }
}
