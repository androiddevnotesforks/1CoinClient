package com.finance_tracker.finance_tracker.features.preset_currency

sealed interface PresetCurrencyAction {

    object Close: PresetCurrencyAction

    object OpenMainScreen: PresetCurrencyAction
}