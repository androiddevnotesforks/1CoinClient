package com.finance_tracker.finance_tracker.core.domain.models

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.common.getLocaleLanguage
import com.finance_tracker.finance_tracker.domain.models.Currency
import java.util.Locale
import java.util.Currency as JavaCurrency

@Composable
fun Currency.getDisplayName(): String {
    val language = getLocaleLanguage()
    val javaCurrency = JavaCurrency.getInstance(code)
    return javaCurrency.getDisplayName(Locale(language))
}