package com.finance_tracker.finance_tracker.core.common.math

val Double.stringSign: String
    get() = when {
        this > 0.0 -> "+"
        this < 0.0 -> "-"
        else -> ""
    }