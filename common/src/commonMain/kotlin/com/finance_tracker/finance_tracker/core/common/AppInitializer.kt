package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.core.analytics.AnalyticsTracker
import com.finance_tracker.finance_tracker.core.common.logger.LoggerInitializer
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.data.settings.AccountSettings
import com.finance_tracker.finance_tracker.domain.interactors.AccountsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.CategoriesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.DashboardSettingsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.UserInteractor
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AppInitializer(
    private val userInteractor: UserInteractor,
    private val categoriesInteractor: CategoriesInteractor,
    private val dashboardSettingsInteractor: DashboardSettingsInteractor,
    private val accountsInteractor: AccountsInteractor,
    private val accountSettings: AccountSettings,
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
        initDatabase()
        initAnalytics()
        updateDashboardItems()
    }

    private fun initLogger() {
        loggerInitializer.init()
    }

    private fun initAnalytics() {
        analyticsTracker.init(context)
        launch {
            val userId = userInteractor.getOrCreateUserId()
            analyticsTracker.setUserId(userId)

            categoriesInteractor.getCategoriesCountFlow()
                .distinctUntilChanged()
                .onEach { analyticsTracker.setUserProperty(UserPropCategoriesCount, it) }
                .launchIn(this)

            accountsInteractor.getAccountsCountFlow()
                .distinctUntilChanged()
                .onEach { analyticsTracker.setUserProperty(UserPropAccountsCount, it) }
                .launchIn(this)
        }
    }

    private fun updateDashboardItems() {
        launch {
            dashboardSettingsInteractor.updateDashboardItems()
        }
    }

    private fun initDatabase() {
        launch {
            if (!accountSettings.isInitDefaultData()) {
                databaseInitializer.init(context)
                accountSettings.setIsInitDefaultData(true)
            }
        }
    }

    companion object {
        private const val UserPropAccountsCount = "accounts_count"
        private const val UserPropCategoriesCount = "categories_count"
    }
}