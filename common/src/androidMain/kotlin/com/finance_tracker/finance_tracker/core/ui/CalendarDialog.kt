package com.finance_tracker.finance_tracker.core.ui

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.common.R
import com.finance_tracker.finance_tracker.core.common.LocalContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalDate as JavaLocalDate

private val todayDate = JavaLocalDate.now().toKotlinLocalDate()

@Composable
@Suppress("MissingModifierDefaultValue", "ModifierParameterPosition")
actual fun CalendarDialog(
    minDate: Long,
    maxDate: Long,
    modifier: Modifier,
    onControllerCreate: (CalendarDialogController) -> Unit,
    onDateChangeListener: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context, R.style.CalendarDialogTheme,
        { _, year, month, dayOfMonth ->
            onDateChangeListener(
                LocalDate(year, month + 1, dayOfMonth)
            )
        }, todayDate.year, todayDate.monthNumber, todayDate.dayOfMonth
    )
    datePickerDialog.window?.setBackgroundDrawableResource(R.drawable.calendar_dialog_background)
    datePickerDialog.apply {
        datePicker.minDate = minDate
        datePicker.maxDate = maxDate
    }
    LaunchedEffect(datePickerDialog) {
        onControllerCreate(getCalendarDialogController(datePickerDialog))
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