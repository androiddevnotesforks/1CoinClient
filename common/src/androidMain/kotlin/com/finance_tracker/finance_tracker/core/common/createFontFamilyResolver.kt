package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.createFontFamilyResolver as androidCreateFontFamilyResolver

actual fun createFontFamilyResolver(): FontFamily.Resolver {
    return androidCreateFontFamilyResolver(ActivityContextStorage.getContext())
}