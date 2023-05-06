package com.finance_tracker.finance_tracker.core.common

fun Double.toLimitedFloat(): Float {
    return toFloat().coerceIn(
        minimumValue = Float.MIN_VALUE,
        maximumValue = Float.MAX_VALUE
    )
}