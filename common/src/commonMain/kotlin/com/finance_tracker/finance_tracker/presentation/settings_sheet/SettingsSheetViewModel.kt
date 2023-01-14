package com.finance_tracker.finance_tracker.presentation.settings_sheet

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.UserInteractor
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.settings_sheet.analytics.SettingsSheetAnalytics
import com.fincance_tracker.fincance_tracker.BuildKonfig
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsSheetViewModel(
    private val currenciesInteractor: CurrenciesInteractor,
    private val userInteractor: UserInteractor,
    private val settingsSheetAnalytics: SettingsSheetAnalytics,
): BaseViewModel<SettingsSheetAction>() {

    val chosenCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = Currency.default)
    val isSendingUsageDataEnabled = userInteractor.isAnalyticsEnabledFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    val userId = flow { emit(userInteractor.getOrCreateUserId()) }
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = "")
    val versionName = BuildKonfig.appVersion

    init {
        settingsSheetAnalytics.trackScreenOpen()
    }

    fun onCurrencySelect(currency: Currency) {
        viewModelScope.launch {
            settingsSheetAnalytics.trackMainCurrencySelect(currency)
            currenciesInteractor.savePrimaryCurrency(currency)
        }
    }

    fun onSendingUsageDataClick(isEnabled: Boolean) {
        settingsSheetAnalytics.trackSendingUsageDataSwitchClick(isEnabled)
        viewModelScope.launch {
            userInteractor.saveIsAnalyticsEnabled(isEnabled)
        }
    }

    fun onSendingUsageDataInfoClick() {
        settingsSheetAnalytics.trackSendingUsageDataInfoClick()
        viewAction = SettingsSheetAction.ShowUsageDataInfoDialog
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

    fun onCopyUserId() {
        settingsSheetAnalytics.trackCopyUserIdClick()

    }

    companion object {
        private const val telegramUri = "https://t.me/+FFK1aCS6uJs1NTBi"
    }
}