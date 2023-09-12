package com.finance_tracker.finance_tracker.core.common.keyboard

import com.finance_tracker.finance_tracker.core.common.formatters.getNumberSeparatorSymbol
import kotlinx.collections.immutable.persistentListOf

sealed class KeyboardAction {
    class Operation(val operation: String) : KeyboardAction()
    class Digit(val digit: String) : KeyboardAction()
    class NumberSeparator(val char: String = getNumberSeparatorSymbol()) : KeyboardAction()
    object Delete : KeyboardAction()
}

val arithmeticOperations = persistentListOf("+", "-", "*", "/")

val arithmeticActions = arithmeticOperations.map { KeyboardAction.Operation(it) }

val numPadActions = persistentListOf(
    KeyboardAction.Digit("1"), KeyboardAction.Digit("2"), KeyboardAction.Digit("3"),
    KeyboardAction.Digit("4"), KeyboardAction.Digit("5"), KeyboardAction.Digit("6"),
    KeyboardAction.Digit("7"), KeyboardAction.Digit("8"), KeyboardAction.Digit("9"),
    KeyboardAction.NumberSeparator(), KeyboardAction.Digit("0"), KeyboardAction.Delete,
)