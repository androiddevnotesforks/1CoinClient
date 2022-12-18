package com.finance_tracker.finance_tracker.presentation.category_settings.di

import com.finance_tracker.finance_tracker.presentation.category_settings.CategorySettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val categorySettingsModule = module {
    factoryOf(::CategorySettingsViewModel)
}