package com.finance_tracker.finance_tracker

import androidx.annotation.DrawableRes

sealed class NavigationItem(
    val route: String,
    @DrawableRes
    val iconRes: Int,
    val title: String
) {
    object Main : NavigationItem(
        route = "main",
        iconRes = R.drawable.ic_main,
        title = "Главная"
    )
    object Operations : NavigationItem(
        route = "operations",
        iconRes = R.drawable.ic_operations,
        title = "Операции"
    )
    object Else : NavigationItem(
        route = "more",
        iconRes = R.drawable.ic_else,
        title = "Ещё"
    )
}