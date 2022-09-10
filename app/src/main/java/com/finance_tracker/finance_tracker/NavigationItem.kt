package com.finance_tracker.finance_tracker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class NavigationItem(
    val route: String,
    @DrawableRes
    val iconRes: Int,
    @StringRes
    val title: Int
) {

    object Main : NavigationItem(
        route = "main",
        iconRes = R.drawable.ic_main,
        title = R.string.main_screen_text
    )

    object Operations : NavigationItem(
        route = "operations",
        iconRes = R.drawable.ic_operations,
        title = R.string.operations_screen_text
    )

    object More : NavigationItem(
        route = "more",
        iconRes = R.drawable.ic_more,
        title = R.string.more_screen_text
    )
}