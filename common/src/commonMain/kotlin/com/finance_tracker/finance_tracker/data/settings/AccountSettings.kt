package com.finance_tracker.finance_tracker.data.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class AccountSettings(factory: Settings.Factory) {

    private val settings: ObservableSettings = factory.create("account") as ObservableSettings
    private val flowSettings: FlowSettings = settings.toFlowSettings()

    suspend fun savePrimaryCurrency(currencyCode: String) = flowSettings.putString(KEY_PRIMARY_CURRENCY, currencyCode)

    fun getPrimaryCurrencyCodeFlow(): Flow<String?> = flowSettings.getStringOrNullFlow(KEY_PRIMARY_CURRENCY)

    suspend fun getPrimaryCurrencyCode(): String? = flowSettings.getStringOrNull(KEY_PRIMARY_CURRENCY)

    suspend fun isInitDefaultData(): Boolean = flowSettings.getBoolean(IS_INIT_DEFAULT_DATA, false)

    suspend fun setIsInitDefaultData(value: Boolean) = flowSettings.putBoolean(IS_INIT_DEFAULT_DATA, value)

    suspend fun isInitAccountPositions() = flowSettings.getBoolean(IS_INIT_ACCOUNT_POSITION, false)

    suspend fun setIsInitAccountPositions(value: Boolean) = flowSettings.putBoolean(IS_INIT_ACCOUNT_POSITION, value)

    companion object {
        private const val KEY_PRIMARY_CURRENCY = "primary_currency"
        private const val IS_INIT_DEFAULT_DATA = "is_init_default_data"
        private const val IS_INIT_ACCOUNT_POSITION = "is_init_account_position"
    }
}