package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun CoinTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    appBarHeight: Dp = AppBarHeight
) {
    val backgroundColor = CoinTheme.color.background
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColorFor(CoinTheme.color.background),
        elevation = AppBarDefaults.TopAppBarElevation
    ) {
        TopAppBar(
            modifier = Modifier
                .statusBarsPadding()
                .height(appBarHeight),
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            backgroundColor = Color.Transparent,
            contentColor = Color.Transparent,
            elevation = 0.dp
        )
    }
}

val AppBarHeight = 56.dp