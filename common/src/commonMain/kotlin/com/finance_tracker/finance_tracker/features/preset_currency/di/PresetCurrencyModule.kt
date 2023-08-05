package com.finance_tracker.finance_tracker.features.preset_currency.di

import com.finance_tracker.finance_tracker.features.preset_currency.PresetCurrencyViewModel
import com.finance_tracker.finance_tracker.features.preset_currency.analytics.PresetCurrencyAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val presetCurrencyModule = module {
    factoryOf(::PresetCurrencyViewModel)
    factoryOf(::PresetCurrencyAnalytics)
}
