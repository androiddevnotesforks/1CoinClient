package com.finance_tracker.finance_tracker.core.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.LayoutDirection

@Composable
actual fun rememberFixedInsets(): FixedInsets {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    return remember {
        FixedInsets(
            statusBarHeight = systemBarsPadding.calculateTopPadding(),
            navigationBarsPadding = PaddingValues(
                bottom = systemBarsPadding.calculateBottomPadding(),
                start = systemBarsPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = systemBarsPadding.calculateEndPadding(LayoutDirection.Ltr),
            ),
        )
    }
}