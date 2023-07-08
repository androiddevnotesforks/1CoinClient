package com.finance_tracker.finance_tracker.features.settings.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics

class SettingsAnalytics: BaseAnalytics() {

    override val screenName = "SettingsScreen"

    fun trackCategorySettingsClick() {
        trackClick(eventName = "CategorySettings")
    }

    fun trackTelegramCommunityClick() {
        trackClick(eventName = "TelegramCommunity")
    }

    fun trackPrivacyClick() {
        trackClick(eventName = "Privacy")
    }

    fun trackSendingUsageDataSwitchClick(isEnabled: Boolean) {
        trackSelect(
            eventName = "SendingUsageData",
            properties = mapOf(
                "is_enabled" to isEnabled
            )
        )
    }

    fun trackCopyUserIdClick() {
        trackClick(eventName = "CopyUserId")
    }

    fun trackSendingUsageDataInfoClick() {
        trackClick(eventName = "SendingUsageDataInfo")
    }

    fun trackDashboardSettingsClick() {
        trackClick(eventName = "DashboardSettings")
    }

    fun trackSelectCurrencyClick() {
        trackClick(eventName = "SelectCurrency")
    }

    fun trackExportImportClick() {
        trackClick(eventName = "ExportImport")
    }

    fun trackExportClick() {
        trackClick(eventName = "Export")
    }

    fun trackImportClick() {
        trackClick(eventName = "Import")
    }
}