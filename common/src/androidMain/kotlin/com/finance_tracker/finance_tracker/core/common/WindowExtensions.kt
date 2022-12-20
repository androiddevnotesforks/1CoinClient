package com.finance_tracker.finance_tracker.core.common

import android.app.Activity
import androidx.core.view.WindowCompat

actual fun Context.updateSystemBarsConfig(systemBarsConfig: SystemBarsConfig) {
    val isLight = systemBarsConfig.isLight
    val window = (this as Activity).window
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = isLight
        isAppearanceLightNavigationBars = isLight
    }
}
