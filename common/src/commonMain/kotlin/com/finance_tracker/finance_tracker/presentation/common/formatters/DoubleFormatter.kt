package com.finance_tracker.finance_tracker.presentation.common.formatters

import com.finance_tracker.finance_tracker.core.common.NumberFormatters

private val formatter = NumberFormatters.DoubleFormatter()

fun Double.format(): String {
    println("format: $this - ${formatter.format(this)}")
    return formatter.format(this)
}

fun String.parse(): Double? {
    println("parse: $this - ${formatter.parse(this)}")
    return formatter.parse(this)
}