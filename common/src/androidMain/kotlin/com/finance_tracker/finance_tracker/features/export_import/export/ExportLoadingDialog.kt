package com.finance_tracker.finance_tracker.features.export_import.export

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.LoadingDialog

@Composable
fun ExportLoadingDialog(
    component: ExportComponent
) {
    CoinTheme {
        val viewModel = component.viewModel
        DisposableEffect(Unit) {
            viewModel.exportFile(uri = component.uri)
            onDispose {
                viewModel.onDialogDismissed()
            }
        }

        val context = LocalContext.current
        viewModel.watchViewActions { action, _ ->
            handleAction(
                action = action,
                context = context
            )
        }

        LoadingDialog(
            title = MR.strings.export_title,
            description = MR.strings.export_description,
            onCancel = { viewModel.onDismissDialog() }
        )
    }
}