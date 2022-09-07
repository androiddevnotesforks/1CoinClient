package com.finance_tracker.finance_tracker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class NavigationItem(
    @StringRes
    val route: Int,
    @DrawableRes
    val iconRes: Int,
    @StringRes
    val title: Int
) {

    object Main : NavigationItem(
        route = R.string.main,
        iconRes = R.drawable.ic_main,
        title = R.string.main_screen_text
    )

    object Operations : NavigationItem(
        route = R.string.operations,
        iconRes = R.drawable.ic_operations,
        title = R.string.operations_screen_text
    )

    object More : NavigationItem(
        route = R.string.more,
        iconRes = R.drawable.ic_more,
        title = R.string.more_screen_text
    )
}