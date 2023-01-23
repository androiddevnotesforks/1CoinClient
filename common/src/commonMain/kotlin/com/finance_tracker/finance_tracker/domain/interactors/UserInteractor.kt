package com.finance_tracker.finance_tracker.domain.interactors

import com.benasher44.uuid.uuid4
import com.finance_tracker.finance_tracker.data.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(
    private val userRepository: UserRepository
) {

    suspend fun getOrCreateUserId(): String {
        val currentUserId = userRepository.getUserId()
        if (currentUserId != null) return currentUserId

        val newUserId = uuid4().toString()
        userRepository.saveUserId(newUserId)
        return newUserId
    }

    suspend fun saveIsAnalyticsEnabled(enabled: Boolean) {
        userRepository.saveIsAnalyticsEnabled(enabled)
    }

    fun isAnalyticsEnabledFlow(): Flow<Boolean> {
        return userRepository.isAnalyticsEnabledFlow()
    }
}