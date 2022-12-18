package com.finance_tracker.finance_tracker.presentation.analytics.di

import com.finance_tracker.finance_tracker.presentation.analytics.AnalyticsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val analyticsModule = module {
    factoryOf(::AnalyticsViewModel)
}