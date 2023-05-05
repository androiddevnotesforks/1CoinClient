package com.finance_tracker.finance_tracker.features.plans.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import com.finance_tracker.finance_tracker.features.plans.PlansScreenViewModel
import com.finance_tracker.finance_tracker.features.plans.analytics.PlansAnalytics

internal val plansModule = module {
    factoryOf(::PlansScreenViewModel)
    factoryOf(::PlansAnalytics)
}