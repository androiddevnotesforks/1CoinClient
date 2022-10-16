package com.finance_tracker.finance_tracker.presentation.tabs_navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.presentation.destinations.AccountsScreenDestination
import com.finance_tracker.finance_tracker.presentation.destinations.AnalyticsScreenDestination
import com.finance_tracker.finance_tracker.presentation.destinations.HomeScreenDestination
import com.finance_tracker.finance_tracker.presentation.destinations.TransactionsScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

sealed class BottomNavigationItem(
    val direction: DirectionDestinationSpec,
    @StringRes
    val label: Int,
    @DrawableRes
    private val activeIconRes: Int,
    @DrawableRes
    private val inactiveIconRes: Int,
) {

    @DrawableRes
    fun provideIcon(isSelected: Boolean): Int {
        return if (isSelected) {
            activeIconRes
        } else {
            inactiveIconRes
        }
    }

    object Home : BottomNavigationItem(
        direction = HomeScreenDestination,
        activeIconRes = R.drawable.ic_home_active,
        inactiveIconRes = R.drawable.ic_home_inactive,
        label = R.string.tab_home
    )

    object Transactions : BottomNavigationItem(
        direction = TransactionsScreenDestination,
        activeIconRes = R.drawable.ic_transactions_active,
        inactiveIconRes = R.drawable.ic_transactions_inactive,
        label = R.string.tab_transactions
    )

    object Accounts : BottomNavigationItem(
        direction = AccountsScreenDestination,
        activeIconRes = R.drawable.ic_wallet_active,
        inactiveIconRes = R.drawable.ic_wallet_inactive,
        label = R.string.tab_accounts
    )

    object Analytics : BottomNavigationItem(
        direction = AnalyticsScreenDestination,
        activeIconRes = R.drawable.ic_analytics_active,
        inactiveIconRes = R.drawable.ic_analytics_inactive,
        label = R.string.tab_analytics
    )
}