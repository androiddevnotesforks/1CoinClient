package com.finance_tracker.finance_tracker.presentation.common.formatters

import com.finance_tracker.finance_tracker.core.common.NumberFormatters

private val formatter = NumberFormatters.DoubleFormatter()

fun Double.format(): String {
    return formatter.format(this)
}

fun String.parse(): Double? {
    return formatter.parse(this)
}