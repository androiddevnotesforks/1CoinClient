package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Suppress("MissingModifierDefaultValue", "ModifierParameterPosition")
@Composable
actual fun CoinGridDropdownMenu(
    columnSize: Dp,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    xOffset: Dp,
    yOffset: Dp,
    content: LazyGridScope.() -> Unit
) {
    // TODO: iOS. CoinGridDropdownMenu
}