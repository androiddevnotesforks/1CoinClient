package com.finance_tracker.finance_tracker.features.plans.setup

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage

fun handleAction(
    action: SetupPlanAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val navController = baseLocalsStorage.rootController.findRootController()
    when (action) {
        SetupPlanAction.Close -> {
            navController.popBackStack()
        }
        is SetupPlanAction.DismissDialog -> {
            navController.findModalController().popBackStack(
                key = action.dialogKey,
                animate = false
            )
        }
    }
}