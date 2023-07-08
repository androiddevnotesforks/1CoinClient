package com.finance_tracker.finance_tracker.features.export_import.import

sealed interface ImportAction {

    data class DismissDialog(val dialogKey: String): ImportAction
}