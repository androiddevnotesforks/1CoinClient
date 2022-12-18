package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberFixedInsets(): FixedInsets {
    return remember { FixedInsets() }
}