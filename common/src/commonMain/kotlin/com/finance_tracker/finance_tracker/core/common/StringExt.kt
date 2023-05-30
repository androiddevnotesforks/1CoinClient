package com.finance_tracker.finance_tracker.core.common

@Suppress("UnnecessaryParentheses")
fun String.isNotEmptyAmount(): Boolean {
    return (this.toDoubleOrNull() ?: 0.0) > 0.0
}
