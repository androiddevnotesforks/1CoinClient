package com.finance_tracker.finance_tracker.core.common.formatters

import com.finance_tracker.finance_tracker.core.common.runSafeCatching
import java.text.DecimalFormatSymbols
import java.text.NumberFormat

internal class DoubleFormatter {
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

        return runSafeCatching {
            formatter.parse(text).toDouble()
        }.getOrNull()
    }
}

actual fun Double.format(): String {
    return DoubleFormatter().format(this)
}

actual fun String.parseToDouble(): Double? {
    return DoubleFormatter().parse(this)
}

actual fun getNumberSeparatorSymbol(): String {
    return DecimalFormatSymbols.getInstance().decimalSeparator.toString()
}