package com.finance_tracker.finance_tracker.features.export_import.export

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage

fun handleAction(
    action: ExportAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val rootController = baseLocalsStorage.rootController

    when (action) {
        is ExportAction.DismissDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.popBackStack(action.dialogKey, animate = true)
        }
    }
}