package com.finance_tracker.finance_tracker.presentation.settings_sheet.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.Currency

class SettingsSheetAnalytics: BaseAnalytics() {

    override val screenName = "SettingsSheet"

    fun trackMainCurrencySelect(currency: Currency) {
        trackSelect(
            eventName = "MainCurrency",
            properties = mapOf(
                "currency" to currency.code
            )
        )
    }

    fun trackChooseCurrencyClick(currency: Currency) {
        trackClick(
            eventName = "MainCurrency",
            properties = mapOf(
                "currency" to currency.code
            )
        )
    }

    fun trackCategorySettingsClick() {
        trackClick(eventName = "CategorySettings")
    }

    fun trackTelegramCommunityClick() {
        trackClick(eventName = "TelegramCommunity")
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
}