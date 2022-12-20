package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class FixedInsets(
    val statusBarHeight: Dp = 0.dp,
    val navigationBarsHeight: Dp = 0.dp,
)

val LocalFixedInsets = compositionLocalOf { FixedInsets() }