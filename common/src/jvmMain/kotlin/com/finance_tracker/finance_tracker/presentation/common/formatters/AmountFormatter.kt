package com.finance_tracker.finance_tracker.presentation.common.formatters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import com.finance_tracker.finance_tracker.core.common.locale.toJavaLocale
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale as JavaLocale

@Composable
actual fun formatAmount(number: Double, currencyCode: String): String {
    val locale = Locale.current
    val amountFormatter by remember(locale) {
        derivedStateOf { AmountFormat(locale.toJavaLocale()) }
    }
    return amountFormatter.format(number, currencyCode)
}

class AmountFormat(locale: JavaLocale) {

    private val formatter = NumberFormat.getCurrencyInstance(locale).apply {
        maximumFractionDigits = 2
        minimumFractionDigits = 0
    }

    fun format(number: Double, currencyCode: String): String {
        setCurrencyCode(currencyCode)
        return formatter.format(number)
    }

    private fun setCurrencyCode(code: String) {
        formatter.currency = Currency.getInstance(code)
    }
}