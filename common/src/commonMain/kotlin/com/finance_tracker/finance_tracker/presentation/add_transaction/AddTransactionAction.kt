package com.finance_tracker.finance_tracker.presentation.add_transaction

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage

sealed interface AddTransactionAction {

    object Close: AddTransactionAction

    data class DismissDialog(
        val dialogKey: String
    ): AddTransactionAction
}

fun handleAction(
    action: AddTransactionAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val navController = baseLocalsStorage.rootController.findRootController()
    when (action) {
        AddTransactionAction.Close -> {
            navController.popBackStack()
        }
        is AddTransactionAction.DismissDialog -> {
            navController.findModalController().popBackStack(
                key = action.dialogKey,
                animate = false
            )
        }
    }
}