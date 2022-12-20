package com.finance_tracker.finance_tracker.core.common

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable

@Composable
actual fun getFixedInsets(): FixedInsets {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val navigationBarsPadding = WindowInsets.navigationBars.asPaddingValues()
    return FixedInsets(
        statusBarHeight = systemBarsPadding.calculateTopPadding(),
        navigationBarsHeight = navigationBarsPadding.calculateBottomPadding()
    )
}