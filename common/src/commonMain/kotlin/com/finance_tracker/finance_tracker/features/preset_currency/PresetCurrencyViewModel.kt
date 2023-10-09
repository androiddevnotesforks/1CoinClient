package com.finance_tracker.finance_tracker.features.preset_currency

import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.core.common.view_models.ComponentViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.features.preset_currency.analytics.PresetCurrencyAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PresetCurrencyViewModel(
    private val currenciesInteractor: CurrenciesInteractor,
    private val presetCurrencyAnalytics: PresetCurrencyAnalytics
) : ComponentViewModel<Any, PresetCurrencyComponent.Action>() {

    private val searchQueryFlow = MutableStateFlow("")
    private val selectedCurrencyFlow = MutableStateFlow(Currency.default)

    val state = combine(
        searchQueryFlow,
        selectedCurrencyFlow
    ) { searchQuery, selectedCurrency ->
        PresetCurrencyState(
            currencies = Currency.list,
            searchQuery = searchQuery,
            selectedCurrency = selectedCurrency
        )
    }.stateIn(viewModelScope, initialValue = PresetCurrencyState())

    fun onSearchQueryChange(query: String) {
        searchQueryFlow.value = query
    }

    fun onCurrencySelect(currency: Currency) {
        selectedCurrencyFlow.value = currency
    }

    fun onContinueClick() {
        viewModelScope.launch {
            val primaryCurrency = selectedCurrencyFlow.value
            presetCurrencyAnalytics.trackContinueClick(selectedCurrencyFlow.value)
            currenciesInteractor.presetPrimaryCurrency(primaryCurrency)
            componentAction = PresetCurrencyComponent.Action.OpenMainScreen
        }
    }
}
