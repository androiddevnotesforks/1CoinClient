package com.finance_tracker.finance_tracker.features.add_transaction.views

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.ui.dialogs.DeleteAlertDialog
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun DeleteTransactionDialog(
    onDeleteTransactionClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    DeleteAlertDialog(
        titleEntity = stringResource(MR.strings.deleting_transaction),
        onCancelClick = onCancelClick,
        onDeleteClick = onDeleteTransactionClick
    )
}