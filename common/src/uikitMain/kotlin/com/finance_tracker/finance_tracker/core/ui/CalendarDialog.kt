package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate

@Suppress("ModifierParameterPosition", "MissingModifierDefaultValue")
@Composable
actual fun CalendarDialog(
    minDate: Long,
    maxDate: Long,
    modifier: Modifier,
    onControllerCreate: (CalendarDialogController) -> Unit,
    onDateChangeListener: (LocalDate) -> Unit
) {
    // TODO: iOS. CalendarDialog
}