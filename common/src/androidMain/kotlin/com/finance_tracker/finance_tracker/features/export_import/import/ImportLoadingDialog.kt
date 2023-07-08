package com.finance_tracker.finance_tracker.features.export_import.import

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.core.ui.LoadingDialog

@Composable
fun ImportLoadingDialog(
    dialogKey: String,
    uri: String
) {
    ComposeScreen<ImportViewModel> { viewModel ->

        DisposableEffect(Unit) {
            viewModel.importFile(
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
            title = MR.strings.import_title,
            description = MR.strings.import_description,
            onCancel = { viewModel.onDismissDialog(dialogKey) }
        )
    }
}