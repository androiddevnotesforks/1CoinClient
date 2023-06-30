package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.core.common.formatters.evaluateDoubleWithReplace
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.github.murzagalin.evaluator.Evaluator

@Suppress("UnnecessaryParentheses")
fun String.isNotEmptyAmount(): Boolean {
    return (this.toDoubleOrNull() ?: 0.0) > 0.0
}

@Suppress("SwallowedException")
fun String.isCalculableExpression(evaluator: Evaluator): Boolean =
    try {
        evaluator.evaluateDoubleWithReplace(this).format()
        true
    } catch (exception: IllegalArgumentException) {
        false
    }
