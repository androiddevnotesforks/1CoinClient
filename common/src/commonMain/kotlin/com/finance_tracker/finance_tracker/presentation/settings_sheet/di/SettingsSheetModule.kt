package com.finance_tracker.finance_tracker.presentation.settings_sheet.di

import com.finance_tracker.finance_tracker.presentation.settings_sheet.SettingsSheetViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val settingsSheetModule = module {
    factoryOf(::SettingsSheetViewModel)
}