package com.finance_tracker.finance_tracker.core.ui

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.LocalContext
import java.time.LocalDate

@Composable
actual fun CalendarDialog(
    modifier: Modifier,
    onControllerCreate: (CalendarDialogController) -> Unit,
    onDateChangeListener: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateChangeListener.invoke(LocalDate.of(year, month, dayOfMonth))
        }, 2022, 8, 8
    )
    LaunchedEffect(datePickerDialog) {
        onControllerCreate.invoke(getCalendarDialogController(datePickerDialog))
    }
}

private fun getCalendarDialogController(datePickerDialog: DatePickerDialog): CalendarDialogController {
    return object : CalendarDialogController {
        override fun show() {
            datePickerDialog.show()
        }

        override fun cancel() {
            datePickerDialog.cancel()
        }
    }
}