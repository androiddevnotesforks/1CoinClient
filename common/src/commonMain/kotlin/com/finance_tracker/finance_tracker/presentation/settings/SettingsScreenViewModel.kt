package com.finance_tracker.finance_tracker.presentation.settings

import com.finance_tracker.finance_tracker.BuildKonfig
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.UserInteractor
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.settings.analytics.SettingsAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val currenciesInteractor: CurrenciesInteractor,
    private val userInteractor: UserInteractor,
    private val settingsAnalytics: SettingsAnalytics,
): BaseViewModel<SettingsScreenAction>() {

    private val _userEmail = MutableStateFlow("")
    val userEmail = _userEmail.asStateFlow()

    private val _isUserAuthorized = MutableStateFlow(false)
    val isUserAuthorized = _isUserAuthorized.asStateFlow()

    val chosenCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = Currency.default)

    val isSendingUsageDataEnabled = userInteractor.isAnalyticsEnabledFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    val userId = flow { emit(userInteractor.getOrCreateUserId()) }
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = "")

    val versionName = BuildKonfig.appVersion

    fun onBackClick() {
        settingsAnalytics.trackBackClick()
        viewAction = SettingsScreenAction.Close
    }

    fun onCurrencySelect(currency: Currency) {
        viewModelScope.launch {
            settingsAnalytics.trackMainCurrencySelect(currency)
            currenciesInteractor.savePrimaryCurrency(currency)
        }
    }

    fun onCurrencyClick(currency: Currency) {
        settingsAnalytics.trackChooseCurrencyClick(currency)
    }

    fun onCategorySettingsClick() {
        settingsAnalytics.trackCategorySettingsClick()
        viewAction = SettingsScreenAction.OpenCategorySettingsScreen
    }

    fun onSendingUsageDataClick(isEnabled: Boolean) {
        settingsAnalytics.trackSendingUsageDataSwitchClick(isEnabled)
        viewModelScope.launch {
            userInteractor.saveIsAnalyticsEnabled(isEnabled)
        }
    }

    fun onSendingUsageDataInfoClick() {
        settingsAnalytics.trackSendingUsageDataInfoClick()
        viewAction = SettingsScreenAction.ShowUsageDataInfoDialog
    }

    fun onTelegramCommunityClick() {
        settingsAnalytics.trackTelegramCommunityClick()
        viewAction = SettingsScreenAction.OpenUri(telegramUri)
    }

    fun onCopyUserId() {
        settingsAnalytics.trackCopyUserIdClick()
        viewAction = SettingsScreenAction.CopyUserId(
            userId = userId.value
        )
    }

    fun onDashboardSettingsClick() {
        settingsAnalytics.trackDashboardSettingsClick()
        viewAction = SettingsScreenAction.OpenDashboardSettingsScreen
    }

    companion object {
        private const val telegramUri = "https://t.me/+FFK1aCS6uJs1NTBi"
    }

}