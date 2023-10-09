package com.finance_tracker.finance_tracker.core.common

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@Composable
actual fun updateSystemBarsConfig(
    systemBarsConfig: SystemBarsConfig,
    isDarkTheme: Boolean
) {
    val activity = LocalContext.current as? Activity ?: return
    val isStatusBarLight = systemBarsConfig.isStatusBarLight
    val isNavigationBarLight = systemBarsConfig.isNavigationBarLight
    val window = activity.window
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = isStatusBarLight ?: !isDarkTheme
        isAppearanceLightNavigationBars = isNavigationBarLight ?: !isDarkTheme
    }
}
