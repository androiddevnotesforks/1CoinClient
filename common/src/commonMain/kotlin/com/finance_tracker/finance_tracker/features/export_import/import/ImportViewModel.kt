package com.finance_tracker.finance_tracker.features.export_import.import

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.hideSnackbar
import com.finance_tracker.finance_tracker.core.common.view_models.showPreviousScreenSnackbar
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarActionState
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarState
import com.finance_tracker.finance_tracker.data.repositories.export_import.ExportImportRepository
import com.finance_tracker.finance_tracker.features.export_import.import.analytics.ImportAnalytics
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImportViewModel(
    private val importAnalytics: ImportAnalytics,
    private val exportImportRepository: ExportImportRepository
): BaseViewModel<ImportAction>() {

    private var importingFileJob: Job? = null

    @Suppress("UnusedPrivateMember")
    fun importFile(
        dialogKey: String,
        uri: String
    ) {
        importingFileJob = viewModelScope.launch {
            runCatching {
                // TODO: UnusedPrivateMember
                val result = exportImportRepository.import(uri)
            }
                .onSuccess {
                    showPreviousScreenSnackbar(
                        snackbarState = SnackbarState.Success(
                            textResId = MR.strings.toast_import_completed,
                            actionState = SnackbarActionState.Close(onAction = ::hideSnackbar)
                        )
                    )
                }
                .onFailure {
                    showPreviousScreenSnackbar(
                        snackbarState = SnackbarState.Error(
                            textResId = MR.strings.toast_import_not_completed,
                            actionState = SnackbarActionState.Close(onAction = ::hideSnackbar)
                        )
                    )
                }
            viewAction = ImportAction.DismissDialog(dialogKey)
        }
    }

    fun onDialogDismissed() {
        importingFileJob?.cancel()
        importingFileJob = null
    }

    fun onDismissDialog(dialogKey: String) {
        importAnalytics.trackCancelClick()
        viewAction = ImportAction.DismissDialog(dialogKey)
    }
}