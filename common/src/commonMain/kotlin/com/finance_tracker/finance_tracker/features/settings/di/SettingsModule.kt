package com.finance_tracker.finance_tracker.features.settings.di

import com.finance_tracker.finance_tracker.features.settings.SettingsViewModel
import com.finance_tracker.finance_tracker.features.settings.analytics.SettingsAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val settingsModule = module {
    factoryOf(::SettingsViewModel)
    factoryOf(::SettingsAnalytics)
}