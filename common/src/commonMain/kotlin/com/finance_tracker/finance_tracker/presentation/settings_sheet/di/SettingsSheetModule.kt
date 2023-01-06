package com.finance_tracker.finance_tracker.presentation.settings_sheet.di

import com.finance_tracker.finance_tracker.presentation.settings_sheet.SettingsSheetViewModel
import com.finance_tracker.finance_tracker.presentation.settings_sheet.analytics.SettingsSheetAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val settingsSheetModule = module {
    factoryOf(::SettingsSheetViewModel)
    factoryOf(::SettingsSheetAnalytics)
}