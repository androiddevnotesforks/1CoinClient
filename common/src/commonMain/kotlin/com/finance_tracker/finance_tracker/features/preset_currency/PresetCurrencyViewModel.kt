package com.finance_tracker.finance_tracker.features.preset_currency

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.features.preset_currency.analytics.PresetCurrencyAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PresetCurrencyViewModel(
    private val currenciesInteractor: CurrenciesInteractor,
    private val presetCurrencyAnalytics: PresetCurrencyAnalytics
) : BaseViewModel<PresetCurrencyAction>() {

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

    fun onContinueClick(context: Context) {
        viewModelScope.launch {
            val primaryCurrency = selectedCurrencyFlow.value
            presetCurrencyAnalytics.trackContinueClick(selectedCurrencyFlow.value)
            currenciesInteractor.presetPrimaryCurrency(context, primaryCurrency)
            viewAction = PresetCurrencyAction.OpenMainScreen
        }
    }

    fun onBackClick() {
        presetCurrencyAnalytics.trackBackClick()
        viewAction = PresetCurrencyAction.Close
    }
}
