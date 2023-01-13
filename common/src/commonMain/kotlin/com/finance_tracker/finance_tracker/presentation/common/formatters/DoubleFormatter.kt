package com.finance_tracker.finance_tracker.presentation.common.formatters

import com.finance_tracker.finance_tracker.core.common.number_formatters.formatDouble
import com.finance_tracker.finance_tracker.core.common.number_formatters.parseDouble

fun Double.format(): String {
    return formatDouble(this)
}

fun String.parse(): Double? {
    return parseDouble(this)
}