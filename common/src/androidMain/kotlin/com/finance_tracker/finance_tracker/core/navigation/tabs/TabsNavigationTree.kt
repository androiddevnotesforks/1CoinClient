package com.finance_tracker.finance_tracker.core.navigation.tabs

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.features.analytics.AnalyticsScreen
import com.finance_tracker.finance_tracker.features.home.HomeScreen
import com.finance_tracker.finance_tracker.features.plans.overview.PlansOverviewScreen
import com.finance_tracker.finance_tracker.features.tabs_navigation.tabs.AnalyticsTab
import com.finance_tracker.finance_tracker.features.tabs_navigation.tabs.HomeTab
import com.finance_tracker.finance_tracker.features.tabs_navigation.tabs.PlansTab
import com.finance_tracker.finance_tracker.features.tabs_navigation.tabs.TransactionsTab
import com.finance_tracker.finance_tracker.features.transactions.TransactionsScreen
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

enum class TabsNavigationTree(
    val tab: TabItem,
    val screen: @Composable () -> Unit
) {
    Home(
        tab = HomeTab(),
        screen = { HomeScreen() }
    ),
    Transactions(
        tab = TransactionsTab(),
        screen = { TransactionsScreen() }
    ),
    Plans(
        tab = PlansTab(),
        screen = { PlansOverviewScreen() }
    ),
    Analytics(
        tab = AnalyticsTab(),
        screen = { AnalyticsScreen() }
    )
}