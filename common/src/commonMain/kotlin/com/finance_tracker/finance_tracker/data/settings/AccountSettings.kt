package com.finance_tracker.finance_tracker.data.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow

@Suppress("RedundantSuspendModifier")
@OptIn(ExperimentalSettingsApi::class)
class AccountSettings(factory: Settings.Factory) {

    private val settings: ObservableSettings = factory.create("account") as ObservableSettings
    private val flowSettings: FlowSettings = settings.toFlowSettings()

    suspend fun savePrimaryCurrency(currencyCode: String) {
        settings.putString(KEY_PRIMARY_CURRENCY, currencyCode)
    }

    fun getPrimaryCurrencyCodeFlow(): Flow<String?> {
        return flowSettings.getStringOrNullFlow(KEY_PRIMARY_CURRENCY)
    }

    suspend fun getPrimaryCurrencyCode(): String? {
        return flowSettings.getStringOrNull(KEY_PRIMARY_CURRENCY)
    }

    companion object {
        private const val KEY_PRIMARY_CURRENCY = "primary_currency"
    }
}