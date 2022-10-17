package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt

fun String?.hexToColor(defaultColor: Color = Color.Transparent): Color {
    if (this == null) return defaultColor

    return hexToColorOrNull() ?: defaultColor
}

fun String?.hexToColorOrNull(): Color? {
    if (this == null) return null
    val maxCharCount = 8

    return runCatching {
        val hexCode = this.removePrefix("#")
            .let { rawHexCode ->
                if (rawHexCode.length < maxCharCount) {
                    "F".repeat(maxCharCount - rawHexCode.length) + rawHexCode
                } else {
                    rawHexCode
                }
            }
        Color("#${hexCode}".toColorInt())
    }.getOrNull()
}

fun Color.toHexString(): String {
    return "#${Integer.toHexString(toArgb()).takeLast(6)}"
}