package com.finance_tracker.finance_tracker.features.export_import.import

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage

fun handleAction(
    action: ImportAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val rootController = baseLocalsStorage.rootController

    when (action) {
        is ImportAction.DismissDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.popBackStack(action.dialogKey, animate = true)
        }
    }
}