package com.finance_tracker.finance_tracker.presentation.settings

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.models.Currency
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsSheetViewModel(
    private val currenciesInteractor: CurrenciesInteractor
): KViewModel() {

    val chosenCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = Currency.default)

    fun onCurrencySelect(currency: Currency) {
        viewModelScope.launch {
            currenciesInteractor.savePrimaryCurrency(currency)
        }
    }
}