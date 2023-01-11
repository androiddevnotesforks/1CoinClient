package com.finance_tracker.finance_tracker.core.ui

import android.os.Build
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Shape

actual fun getBottomAppBarCutoutShape(): Shape? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        CircleShape
    } else {
        null
    }
}