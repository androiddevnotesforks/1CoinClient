package com.finance_tracker.finance_tracker.features.settings

sealed interface SettingsScreenAction {
    object Close: SettingsScreenAction
    object OpenCategorySettingsScreen: SettingsScreenAction
    object ShowUsageDataInfoDialog: SettingsScreenAction
    object OpenDashboardSettingsScreen: SettingsScreenAction
    object OpenExportImportDialog: SettingsScreenAction
    data class OpenExportDialog(val uri: String): SettingsScreenAction
    object ChooseExportDirectory: SettingsScreenAction
    data class OpenImportDialog(val uri: String): SettingsScreenAction
    object ChooseImportFile: SettingsScreenAction
    object DismissAllDialogs: SettingsScreenAction
    object OpenPrivacyScreen: SettingsScreenAction
    object OpenSelectCurrencyScreen: SettingsScreenAction
    data class OpenUri(val uri: String): SettingsScreenAction
    data class CopyUserId(val userId: String): SettingsScreenAction
}