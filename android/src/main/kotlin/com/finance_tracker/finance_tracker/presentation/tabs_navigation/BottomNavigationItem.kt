package com.finance_tracker.finance_tracker.presentation.tabs_navigation

import com.finance_tracker.finance_tracker.presentation.destinations.AccountsScreenDestination
import com.finance_tracker.finance_tracker.presentation.destinations.AnalyticsScreenDestination
import com.finance_tracker.finance_tracker.presentation.destinations.HomeScreenDestination
import com.finance_tracker.finance_tracker.presentation.destinations.TransactionsScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

sealed class BottomNavigationItem(
    val direction: DirectionDestinationSpec,
    val labelStringId: String,
    private val activeIconDrawableId: String,
    private val inactiveIconDrawableId: String,
) {

    fun provideIcon(isSelected: Boolean): String {
        return if (isSelected) {
            activeIconDrawableId
        } else {
            inactiveIconDrawableId
        }
    }

    object Home : BottomNavigationItem(
        direction = HomeScreenDestination,
        activeIconDrawableId = "ic_home_active",
        inactiveIconDrawableId = "ic_home_inactive",
        labelStringId = "tab_home"
    )

    object Transactions : BottomNavigationItem(
        direction = TransactionsScreenDestination,
        activeIconDrawableId = "ic_transactions_active",
        inactiveIconDrawableId = "ic_transactions_inactive",
        labelStringId = "tab_transactions"
    )

    object Accounts : BottomNavigationItem(
        direction = AccountsScreenDestination,
        activeIconDrawableId = "ic_wallet_active",
        inactiveIconDrawableId = "ic_wallet_inactive",
        labelStringId = "tab_accounts"
    )

    object Analytics : BottomNavigationItem(
        direction = AnalyticsScreenDestination,
        activeIconDrawableId = "ic_analytics_active",
        inactiveIconDrawableId = "ic_analytics_inactive",
        labelStringId = "tab_analytics"
    )
}