package com.finance_tracker.finance_tracker.presentation.settings_sheet

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.settings_sheet.analytics.SettingsSheetAnalytics
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsSheetViewModel(
    private val currenciesInteractor: CurrenciesInteractor,
    private val settingsSheetAnalytics: SettingsSheetAnalytics
): BaseViewModel<SettingsSheetAction>() {

    init {
        settingsSheetAnalytics.trackScreenOpen()
    }

    val chosenCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = Currency.default)

    fun onCurrencySelect(currency: Currency) {
        viewModelScope.launch {
            settingsSheetAnalytics.trackMainCurrencySelect(currency)
            currenciesInteractor.savePrimaryCurrency(currency)
        }
    }

    fun onCategorySettingsClick(dialogKey: String) {
        settingsSheetAnalytics.trackCategorySettingsClick()
        viewAction = SettingsSheetAction.DismissDialog(dialogKey)
        viewAction = SettingsSheetAction.OpenCategorySettingsScreen
    }

    fun onTelegramCommunityClick() {
        settingsSheetAnalytics.trackTelegramCommunityClick()
        viewAction = SettingsSheetAction.OpenUri(telegramUri)
    }

    fun onCurrencyClick(currency: Currency) {
        settingsSheetAnalytics.trackChooseCurrencyClick(currency)
    }

    companion object {
        private const val telegramUri = "https://t.me/+FFK1aCS6uJs1NTBi"
    }
}