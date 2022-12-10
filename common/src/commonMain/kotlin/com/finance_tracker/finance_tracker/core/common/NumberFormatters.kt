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

        fun format(number: Double, currencyCode: String): String {
            setCurrencyCode(currencyCode)
            return formatter.format(number)
        }

        private fun setCurrencyCode(code: String) {
            formatter.currency = Currency.getInstance(code)
        }
    }

    class DoubleFormatter {

        private val formatter = NumberFormat.getNumberInstance().apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 0
            isGroupingUsed = false
        }

        fun format(number: Double): String {
            return formatter.format(number)
        }

        fun parse(text: String): Double? {
            if (text.isBlank()) return null

            val formattedText = text.replace(",", ".")
            return runCatching {
                formatter.parse(formattedText).toDouble()
            }.getOrNull()
        }
    }
}