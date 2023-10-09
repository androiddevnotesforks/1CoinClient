package com.finance_tracker.finance_tracker.features.export_import.import

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.LoadingDialog

@Composable
fun ImportLoadingDialog(
    component: ImportComponent
) {
    CoinTheme {
        val viewModel = component.viewModel
        DisposableEffect(Unit) {
            viewModel.importFile(uri = component.uri)
            onDispose {
                viewModel.onDialogDismissed()
            }
        }

        LoadingDialog(
            title = MR.strings.import_title,
            description = MR.strings.import_description,
            onCancel = viewModel::onDismissDialog
        )
    }
}