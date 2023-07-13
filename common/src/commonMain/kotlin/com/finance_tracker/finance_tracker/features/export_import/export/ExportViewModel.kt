package com.finance_tracker.finance_tracker.features.export_import.export

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.hideSnackbar
import com.finance_tracker.finance_tracker.core.common.view_models.showPreviousScreenSnackbar
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarActionState
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarState
import com.finance_tracker.finance_tracker.domain.interactors.ExportImportInteractor
import com.finance_tracker.finance_tracker.features.export_import.export.analytics.ExportAnalytics
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExportViewModel(
    private val exportAnalytics: ExportAnalytics,
    private val exportImportInteractor: ExportImportInteractor
): BaseViewModel<ExportAction>() {

    private var exportingFileJob: Job? = null

    fun exportFile(
        dialogKey: String,
        uri: String
    ) {
        exportingFileJob = viewModelScope.launch {
            runCatching { exportImportInteractor.export(uri) }
                .onSuccess { uri ->
                    viewAction = ExportAction.OpenShareSheet(uri)
                }
                .onFailure {
                    showPreviousScreenSnackbar(
                        snackbarState = SnackbarState.Error(
                            textResId = MR.strings.toast_export_not_completed,
                            actionState = SnackbarActionState.Close(onAction = ::hideSnackbar)
                        )
                    )
                }
            viewAction = ExportAction.DismissDialog(dialogKey)
        }
    }

    fun onDismissDialog(dialogKey: String) {
        exportAnalytics.trackCancelClick()
        viewAction = ExportAction.DismissDialog(dialogKey)
    }

    fun onDialogDismissed() {
        exportingFileJob?.cancel()
        exportingFileJob = null
    }
}