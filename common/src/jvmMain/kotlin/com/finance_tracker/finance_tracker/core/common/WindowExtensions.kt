package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable

@Composable
expect fun updateSystemBarsConfig(
    systemBarsConfig: SystemBarsConfig,
    isDarkTheme: Boolean
)
