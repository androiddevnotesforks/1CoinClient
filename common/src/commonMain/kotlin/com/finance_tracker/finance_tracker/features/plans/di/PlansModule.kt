package com.finance_tracker.finance_tracker.features.plans.di

import com.finance_tracker.finance_tracker.features.plans.overview.PlansOverviewViewModel
import com.finance_tracker.finance_tracker.features.plans.overview.analytics.PlansOverviewAnalytics
import com.finance_tracker.finance_tracker.features.plans.set_limit.SetLimitViewModel
import com.finance_tracker.finance_tracker.features.plans.setup.SetupPlanViewModel
import com.finance_tracker.finance_tracker.features.plans.setup.analytics.SetupPlanAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val plansModule = module {
    factoryOf(::PlansOverviewViewModel)
    factoryOf(::PlansOverviewAnalytics)
    factoryOf(::SetupPlanViewModel)
    factoryOf(::SetupPlanAnalytics)
    factoryOf(::SetLimitViewModel)
}