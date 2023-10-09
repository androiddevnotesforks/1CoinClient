package com.finance_tracker.finance_tracker.features.export_import.export

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.view_models.ComponentViewModel
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
): ComponentViewModel<ExportAction, ExportComponent.Action>() {

    private var exportingFileJob: Job? = null

    fun exportFile(
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
            componentAction = ExportComponent.Action.Close
        }
    }

    fun onDismissDialog() {
        exportAnalytics.trackCancelClick()
        componentAction = ExportComponent.Action.Close
    }

    fun onDialogDismissed() {
        exportingFileJob?.cancel()
        exportingFileJob = null
    }
}