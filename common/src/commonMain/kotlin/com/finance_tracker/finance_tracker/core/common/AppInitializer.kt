package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.core.analytics.AnalyticsTracker
import com.finance_tracker.finance_tracker.core.common.logger.LoggerInitializer
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.domain.interactors.UserInteractor
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AppInitializer(
    private val userInteractor: UserInteractor,
    private val analyticsTracker: AnalyticsTracker,
    private val databaseInitializer: DatabaseInitializer,
    private val loggerInitializer: LoggerInitializer,
    private val context: Context
): CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() +
            CoroutineExceptionHandler { _, throwable ->
                Napier.e("AppInitializer", throwable)
            }

    fun init() {
        initLogger()
        initAnalytics()
        initDatabase()
    }

    private fun initLogger() {
        loggerInitializer.init()
    }

    private fun initAnalytics() {
        analyticsTracker.init(context)
        launch {
            val userId = userInteractor.getOrCreateUserId()
            analyticsTracker.setUserId(userId)
        }
    }

    private fun initDatabase() {
        databaseInitializer.init(context)
    }
}