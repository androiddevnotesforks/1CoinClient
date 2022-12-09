package com.finance_tracker.finance_tracker.core.common

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object NumberFormatters {
    class AmountFormat(locale: Locale) {

        private val formatter = NumberFormat.getCurrencyInstance(locale).apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 0
        }

        var currencyCode: String?
            set(code) {
                formatter.currency = Currency.getInstance(code)
            }
            get() = formatter.currency?.currencyCode

        fun format(number: Double): String {
            return formatter.format(number)
        }
    }
}