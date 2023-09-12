package com.finance_tracker.finance_tracker.features.settings

import com.finance_tracker.finance_tracker.core.common.AppBuildConfig
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.feature_flags.FeaturesManager
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.ThemeInteractor
import com.finance_tracker.finance_tracker.domain.interactors.UserInteractor
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.ThemeMode
import com.finance_tracker.finance_tracker.features.settings.analytics.SettingsAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val userInteractor: UserInteractor,
    private val settingsAnalytics: SettingsAnalytics,
    private val themeInteractor: ThemeInteractor,
    currenciesInteractor: CurrenciesInteractor,
    val featuresManager: FeaturesManager
): BaseViewModel<SettingsScreenAction>() {

    var lastExportImportDialogKey: String? = null

    private val _userEmail = MutableStateFlow("")
    val userEmail = _userEmail.asStateFlow()

    private val _isUserAuthorized = MutableStateFlow(false)
    val isUserAuthorized = _isUserAuthorized.asStateFlow()

    val isSendingUsageDataEnabled = userInteractor.isAnalyticsEnabledFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    val userId = flow { emit(userInteractor.getOrCreateUserId()) }
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = "")

    val primaryCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = Currency.default)

    val versionName = AppBuildConfig.appVersion
    val themes = ThemeMode.getAllThemes()

    val currentTheme = themeInteractor.getThemeMode()

    fun onBackClick() {
        settingsAnalytics.trackBackClick()
        viewAction = SettingsScreenAction.Close
    }

    fun onSelectCurrencyClick() {
        settingsAnalytics.trackSelectCurrencyClick()
        viewAction = SettingsScreenAction.OpenSelectCurrencyScreen
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

    fun onPrivacyClick() {
        settingsAnalytics.trackPrivacyClick()
        viewAction = SettingsScreenAction.OpenPrivacyScreen
    }

    fun onCopyUserId() {
        settingsAnalytics.trackCopyUserIdClick()
        viewAction = SettingsScreenAction.CopyUserId(
            userId = userId.value
        )
    }

    fun onThemeChange(themeMode: ThemeMode) {
        viewModelScope.launch {
            themeInteractor.setThemeMode(themeMode)
        }
    }

    fun onDashboardSettingsClick() {
        settingsAnalytics.trackDashboardSettingsClick()
        viewAction = SettingsScreenAction.OpenDashboardSettingsScreen
    }

    fun onExportImportClick() {
        settingsAnalytics.trackExportImportClick()
        viewAction = SettingsScreenAction.OpenExportImportDialog
    }

    fun onExportClick() {
        settingsAnalytics.trackExportClick()
        viewAction = SettingsScreenAction.DismissAllDialogs
        viewAction = SettingsScreenAction.ChooseExportDirectory
    }

    fun onImportClick() {
        settingsAnalytics.trackImportClick()
        viewAction = SettingsScreenAction.OpenExportImportDialog
        viewAction = SettingsScreenAction.DismissAllDialogs
        viewAction = SettingsScreenAction.ChooseImportFile
    }

    fun onFileChosen(uri: String) {
        viewAction = SettingsScreenAction.DismissAllDialogs
        viewAction = SettingsScreenAction.OpenImportDialog(uri)
    }

    fun onDirectoryChosen(uri: String) {
        viewAction = SettingsScreenAction.OpenExportDialog(uri)
    }

    companion object {
        private const val telegramUri = "https://t.me/+FFK1aCS6uJs1NTBi"
    }

}