package com.finance_tracker.finance_tracker.features.export_import.export

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.features.export_import.export.analytics.ExportAnalytics

class ExportViewModel(
    private val exportAnalytics: ExportAnalytics
): BaseViewModel<ExportAction>() {

    fun onDismissDialog(dialogKey: String) {
        exportAnalytics.trackCancelClick()
        viewAction = ExportAction.DismissDialog(dialogKey)
    }
}