package com.finance_tracker.finance_tracker.core.common

data class TextFieldValue(
    val text: String = "",
    val selection: TextRange = TextRange.Zero,
    val composition: TextRange? = null
)

data class TextRange(val start: Int, val end: Int) {
    companion object {
        val Zero = TextRange(start = 0, end = 0)
    }
}