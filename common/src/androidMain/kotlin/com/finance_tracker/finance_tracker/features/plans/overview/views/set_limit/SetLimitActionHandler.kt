package com.finance_tracker.finance_tracker.features.plans.overview.views.set_limit

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.features.plans.set_limit.SetLimitAction

fun handleAction(
    action: SetLimitAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val navController = baseLocalsStorage.rootController.findRootController()
    when (action) {
        is SetLimitAction.DismissDialog -> {
            navController.findModalController().popBackStack(
                key = action.dialogKey,
                animate = true
            )
        }
    }
}