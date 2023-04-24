package com.finance_tracker.finance_tracker.core.common.formatters

import com.github.murzagalin.evaluator.Evaluator

internal fun Evaluator.evaluateDoubleWithReplace(expression: String) =
    evaluateDouble(expression.replace(",", "."))