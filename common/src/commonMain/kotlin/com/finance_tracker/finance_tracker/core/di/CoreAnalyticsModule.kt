package com.finance_tracker.finance_tracker.core.di

import com.finance_tracker.finance_tracker.presentation.tabs_navigation.analytics.TabsNavigationAnalytics
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val coreAnalyticsModule = module {
    singleOf(::TabsNavigationAnalytics)
}