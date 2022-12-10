package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.finance_tracker.finance_tracker.common.R

actual val RubikFontFamily: FontFamily = FontFamily(
    Font(R.font.rubik_regular, weight = FontWeight.Normal),
    Font(R.font.rubik_medium, weight = FontWeight.Medium)
)