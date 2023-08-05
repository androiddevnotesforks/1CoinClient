package com.finance_tracker.finance_tracker.features.preset_currency

import com.finance_tracker.finance_tracker.domain.models.Currency

data class PresetCurrencyState(
    val currencies: List<Currency> = Currency.list,
    val searchQuery: String = "",
    val selectedCurrency: Currency = Currency.default
)