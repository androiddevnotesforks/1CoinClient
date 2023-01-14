package com.finance_tracker.finance_tracker.core.common.number_formatters

import com.finance_tracker.finance_tracker.core.common.runSafeCatching
import java.text.NumberFormat

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
            return runSafeCatching {
                formatter.parse(formattedText).toDouble()
            }.getOrNull()
        }
}

actual fun formatDouble(number: Double): String {
    return DoubleFormatter().format(number)
}

actual fun parseDouble(text: String): Double? {
    return DoubleFormatter().parse(text)
}