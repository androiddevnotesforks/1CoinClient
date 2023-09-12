package com.finance_tracker.finance_tracker.features.plans.overview.views.plans_overview

import com.finance_tracker.finance_tracker.features.plans.overview.views.plan_period.PlanPeriodCallback

interface PlansOverviewCallback: PlanPeriodCallback {

    fun onPageChange(page: Int)

    fun onSetLimitClick()
}