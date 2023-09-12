package com.finance_tracker.finance_tracker.core.common.keyboard

import com.finance_tracker.finance_tracker.core.common.TextFieldValue
import com.finance_tracker.finance_tracker.core.common.TextRange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal fun MutableStateFlow<TextFieldValue>.applyKeyboardAction(keyboardAction: KeyboardAction) {
    val balance = value.text
    val cursorPosition = value.selection.start

    val (newBalance: String, newCursorPosition: Int) = when (keyboardAction) {
        is KeyboardAction.Operation -> {
            Pair(
                first = balance.insertAtPosition(keyboardAction.operation, cursorPosition),
                second = cursorPosition + 1
            )
        }

        is KeyboardAction.NumberSeparator -> {
            Pair(
                first = balance.insertAtPosition(keyboardAction.char, cursorPosition),
                second = cursorPosition + 1
            )
        }

        KeyboardAction.Delete -> {
            if (cursorPosition == 0) {
                Pair(
                    first = balance,
                    second = cursorPosition
                )
            } else {
                Pair(
                    first = balance.removeRange(cursorPosition - 1, cursorPosition),
                    second = cursorPosition - 1
                )
            }
        }

        is KeyboardAction.Digit -> {
            Pair(
                first = balance.insertAtPosition(keyboardAction.digit, cursorPosition),
                second = cursorPosition + 1
            )
        }
    }

    update {
        value.copy(
            text = newBalance,
            selection = TextRange(
                start = newCursorPosition,
                end = newCursorPosition
            )
        )
    }
}

internal fun TextFieldValue.applyKeyboardAction(
    hasInitialZero: Boolean,
    keyboardAction: KeyboardAction
): TextFieldValue {
    val textValue = this.text
    val cursorPosition = this.selection.start

    val (newBalance: String, newCursorPosition: Int) = when (keyboardAction) {
        is KeyboardAction.Operation -> {
            Pair(
                first = textValue.insertAtPosition(keyboardAction.operation, cursorPosition),
                second = cursorPosition + 1
            )
        }

        is KeyboardAction.NumberSeparator -> {
            Pair(
                first = textValue.insertAtPosition(keyboardAction.char, cursorPosition),
                second = cursorPosition + 1
            )
        }

        KeyboardAction.Delete -> {
            if (cursorPosition == 0) {
                Pair(
                    first = textValue,
                    second = cursorPosition
                )
            } else {
                Pair(
                    first = textValue.removeRange(cursorPosition - 1, cursorPosition),
                    second = cursorPosition - 1
                )
            }
        }

        is KeyboardAction.Digit -> {
            if (hasInitialZero && textValue == "0" && keyboardAction.digit != "0") {
                Pair(
                    first = textValue.replaceRange(
                        range = cursorPosition - 1 until cursorPosition,
                        replacement = keyboardAction.digit
                    ),
                    second = cursorPosition
                )
            }
            else {
                Pair(
                    first = textValue.insertAtPosition(keyboardAction.digit, cursorPosition),
                    second = cursorPosition + 1
                )
            }
        }
    }

    return this.copy(
        text = newBalance,
        selection = TextRange(
            start = newCursorPosition,
            end = newCursorPosition
        )
    )
}

private fun String.insertAtPosition(char: String, position: Int) =
    StringBuilder(this).apply { insert(position, char) }.toString()