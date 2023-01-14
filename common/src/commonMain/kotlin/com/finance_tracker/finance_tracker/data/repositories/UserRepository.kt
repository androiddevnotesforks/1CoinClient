package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import com.finance_tracker.finance_tracker.data.settings.UserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class UserRepository(
    private val userSettings: UserSettings,
    private val analyticsSettings: AnalyticsSettings
) {

    suspend fun saveUserId(userId: String) {
        withContext(Dispatchers.Default) {
            userSettings.saveUserId(userId)
        }
    }

    suspend fun getUserId(): String? {
        return withContext(Dispatchers.Default) {
            userSettings.getUserId()
        }
    }

    suspend fun saveIsAnalyticsEnabled(enabled: Boolean) {
        withContext(Dispatchers.Default) {
            analyticsSettings.saveIsAnalyticsEnabled(enabled)
        }
    }

    fun isAnalyticsEnabledFlow(): Flow<Boolean> {
        return analyticsSettings.isAnalyticsEnabledFlow()
            .flowOn(Dispatchers.Default)
    }
}