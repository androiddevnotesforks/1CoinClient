package com.finance_tracker.finance_tracker.features.settings

sealed interface SettingsAction {
    data object ChooseExportDirectory: SettingsAction
    data object ChooseImportFile: SettingsAction
    data class OpenUri(val uri: String): SettingsAction
    data class CopyUserId(val userId: String): SettingsAction
}