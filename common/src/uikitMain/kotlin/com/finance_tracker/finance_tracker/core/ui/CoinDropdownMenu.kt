package com.finance_tracker.finance_tracker.core.ui

@Suppress("MissingModifierDefaultValue")
@Composable
actual fun DropdownMenuItem(
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    // TODO: iOS. DropdownMenuItem
}

@Suppress("MissingModifierDefaultValue")
@Composable
actual fun CoinDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    xOffset: Dp,
    yOffset: Dp,
    content: @Composable ColumnScope.() -> Unit
) {
    // TODO: iOS. CoinDropdownMenu
}
