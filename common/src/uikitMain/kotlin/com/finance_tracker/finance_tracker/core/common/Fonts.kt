package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

// TODO: iOS. RubikFontFamily
actual val RubikFontFamily: FontFamily = FontFamily(
    Font("font/rubik_regular.ttf", weight = FontWeight.Normal, data = ByteArray(0)),
    Font("font/rubik_medium.ttf", weight = FontWeight.Medium, data = ByteArray(0))
)