package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
@Suppress("MissingModifierDefaultValue", "ModifierParameterPosition", "ReusedModifierInstance")
actual fun CoinGridDropdownMenu(
    columnSize: Dp,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    xOffset: Dp,
    yOffset: Dp,
    content: LazyGridScope.() -> Unit
) {
    CoinTheme {
        GridDropdownMenu(
            columnSize = columnSize,
            modifier = modifier,
            expanded = expanded,
            offset = DpOffset(x = xOffset, y = yOffset),
            onDismissRequest = onDismissRequest,
            content = content
        )
    }
}