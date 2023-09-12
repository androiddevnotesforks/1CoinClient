package com.finance_tracker.finance_tracker.features.export_import.export

sealed interface ExportAction {

    data class OpenShareSheet(val uri: String): ExportAction

    data class DismissDialog(val dialogKey: String): ExportAction

    data class SaveFile(
        val directoryUri: String
    ): ExportAction
}