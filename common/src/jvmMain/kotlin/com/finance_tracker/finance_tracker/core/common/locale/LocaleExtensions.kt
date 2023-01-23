package com.finance_tracker.finance_tracker.core.common.locale

import androidx.compose.ui.text.intl.Locale
import java.util.Locale as JavaLocale

fun Locale.toJavaLocale(): JavaLocale {
    return JavaLocale.forLanguageTag(toLanguageTag())
}