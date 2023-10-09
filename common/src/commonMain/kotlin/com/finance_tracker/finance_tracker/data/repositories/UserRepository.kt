package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.core.common.UniqueDeviceIdProvider
import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import com.finance_tracker.finance_tracker.data.settings.UserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class UserRepository(
    private val uniqueDeviceIdProvider: UniqueDeviceIdProvider,
    private val userSettings: UserSettings,
    private val analyticsSettings: AnalyticsSettings
) {

    suspend fun getOrCreateUserId(): String {
        return withContext(Dispatchers.IO) {
            val currentUserId = userSettings.getUserId()
            if (currentUserId != null) {
                return@withContext currentUserId
            }

            val newUserId = uniqueDeviceIdProvider.getUniqueDeviceId()
            userSettings.saveUserId(newUserId)
            newUserId
        }
    }

    suspend fun saveIsAnalyticsEnabled(enabled: Boolean) {
        withContext(Dispatchers.IO) {
            analyticsSettings.saveIsAnalyticsEnabled(enabled)
        }
    }

    fun isAnalyticsEnabledFlow(): Flow<Boolean> {
        return analyticsSettings.isAnalyticsEnabledFlow()
            .flowOn(Dispatchers.IO)
    }
}