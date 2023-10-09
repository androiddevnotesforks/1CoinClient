package com.finance_tracker.finance_tracker.core.ui.decompose_ext

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.finance_tracker.finance_tracker.core.common.DialogCloseable
import com.finance_tracker.finance_tracker.core.ui.dialogs.BottomSheetDialogSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <Child: Any, Slot: Value<ChildSlot<*, Child>>> Slot.subscribeAlertDialog(
    noinline onDismissRequest: () -> Unit,
    crossinline content: @Composable (Child) -> Unit
) {
    val alertDialogSlot by this.subscribeAsState()
    val alertDialogChild = alertDialogSlot.child?.instance
    if (alertDialogChild != null) {
        AlertDialog(
            onDismissRequest = onDismissRequest
        ) {
            content(alertDialogChild)
        }
    }
}

@Composable
inline fun <Child: DialogCloseable, Slot: Value<ChildSlot<*, Child>>> Slot.subscribeBottomDialog(
    noinline onDismissRequest: () -> Unit,
    crossinline content: @Composable (Child) -> Unit
) {
    val bottomDialogSlot by this.subscribeAsState()
    val bottomDialogChild = bottomDialogSlot.child?.instance
    if (bottomDialogChild != null) {
        BottomSheetDialogSurface(
            onDismissRequest = onDismissRequest,
            closeable = bottomDialogChild.closeable
        ) {
            content(bottomDialogChild)
        }
    }
}