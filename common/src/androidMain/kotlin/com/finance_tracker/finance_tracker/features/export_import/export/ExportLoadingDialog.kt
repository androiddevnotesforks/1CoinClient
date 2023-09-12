package com.finance_tracker.finance_tracker.features.export_import.export

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.core.ui.LoadingDialog

@Composable
fun ExportLoadingDialog(
    dialogKey: String,
    uri: String
) {
    ComposeScreen<ExportViewModel> { viewModel ->

        DisposableEffect(Unit) {
            viewModel.exportFile(
                dialogKey = dialogKey,
                uri = uri
            )
            onDispose {
                viewModel.onDialogDismissed()
            }
        }

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage
            )
        }

        LoadingDialog(
            title = MR.strings.export_title,
            description = MR.strings.export_description,
            onCancel = { viewModel.onDismissDialog(dialogKey) }
        )
    }
}