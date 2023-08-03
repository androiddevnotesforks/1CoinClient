package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.unit.dp
import com.github.lgooddatepicker.components.DatePicker
import com.github.lgooddatepicker.components.DatePickerSettings
import com.github.lgooddatepicker.optionalusertools.DateChangeListener
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlin.time.Duration.Companion.milliseconds
import java.time.LocalDate as JavaLocalDate

@Suppress("MissingModifierDefaultValue", "ModifierParameterPosition")
@Composable
actual fun CalendarDialog(
    minDate: Long,
    maxDate: Long,
    modifier: Modifier,
    onControllerCreate: (CalendarDialogController) -> Unit,
    onDateChangeListener: (LocalDate) -> Unit
) {

    val datePickerSettings: DatePickerSettings by remember {
        mutableStateOf(
            DatePickerSettings().apply {
                visibleClearButton = false
            }
        )
    }
    val datePicker: DatePicker by remember(datePickerSettings) {
        mutableStateOf(DatePicker(datePickerSettings))
    }
    LaunchedEffect(datePicker) {
        onControllerCreate(getCalendarDialogController(datePicker))
    }
    SwingPanel(
        modifier = Modifier.size(0.dp),
        factory = { datePicker }
    )
    LaunchedEffect(datePickerSettings) {
        datePickerSettings.setDateRangeLimits(minDate.toLocalDate(), maxDate.toLocalDate())
    }
    DisposableEffect(datePicker) {
        val dateChangeListener = DateChangeListener {
            onDateChangeListener(it.newDate.toKotlinLocalDate())
        }
        datePicker.addDateChangeListener(dateChangeListener)
        onDispose {
            datePicker.removeDateChangeListener(dateChangeListener)
        }
    }
}

private fun getCalendarDialogController(datePicker: DatePicker): CalendarDialogController {
    return object : CalendarDialogController {
        override fun show() {
            if (!datePicker.isPopupOpen) {
                datePicker.openPopup()
            }
        }

        override fun cancel() {
            if (datePicker.isPopupOpen) {
                datePicker.closePopup()
            }
        }
    }
}

private fun Long.toLocalDate(): JavaLocalDate {
    return JavaLocalDate.ofEpochDay(milliseconds.inWholeDays)
}