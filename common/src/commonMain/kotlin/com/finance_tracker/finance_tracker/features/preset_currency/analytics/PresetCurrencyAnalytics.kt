package com.finance_tracker.finance_tracker.features.preset_currency.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.Currency

class PresetCurrencyAnalytics: BaseAnalytics() {

    override val screenName = "PresetCurrencyScreen"

    fun trackContinueClick(selectedCurrency: Currency) {
        trackClick(
            eventName = "Continue",
            properties = mapOf(
                "code" to selectedCurrency.code
            )
        )
    }
}