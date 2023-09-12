package com.finance_tracker.finance_tracker.features.add_transaction

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.ui.dialogs.NotificationDialog
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.features.add_category.AddCategoryScreenParams
import com.finance_tracker.finance_tracker.features.add_transaction.views.DeleteTransactionDialog
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push

fun handleAction(
    action: AddTransactionAction,
    baseLocalsStorage: BaseLocalsStorage,
    onDeleteTransactionClick: (Transaction, String) -> Unit
) {
    val navController = baseLocalsStorage.rootController.findRootController()
    val modalNavController = navController.findModalController()
    when (action) {
        AddTransactionAction.Close -> {
            navController.popBackStack()
        }

        is AddTransactionAction.DismissDialog -> {
            modalNavController.popBackStack(
                key = action.dialogKey,
                animate = false
            )
        }

        AddTransactionAction.OpenAddAccountScreen -> {
            navController.push(MainNavigationTree.AddAccount.name)
        }

        is AddTransactionAction.OpenAddCategoryScreen -> {
            navController.push(
                screen = MainNavigationTree.AddCategory.name,
                params = AddCategoryScreenParams(
                    transactionType = action.type.toTransactionType()
                )
            )
        }

        is AddTransactionAction.DisplayDeleteTransactionDialog -> {
            modalNavController.present(DialogConfigurations.alert) { dialogKey ->
                DeleteTransactionDialog(
                    onCancelClick = {
                        modalNavController.popBackStack(dialogKey, animate = false)
                    },
                    onDeleteTransactionClick = {
                        action.transaction?.let {
                            onDeleteTransactionClick(it, dialogKey)
                        }
                    }
                )
            }
        }

        AddTransactionAction.DisplayNegativeAmountDialog -> {
            modalNavController.present(DialogConfigurations.alert) { dialogKey: String ->
                NotificationDialog(
                    text = MR.strings.add_transaction_negative_balance,
                    onOkClick = {
                        modalNavController.popBackStack(dialogKey, animate = false)
                    }
                )
            }
        }
        AddTransactionAction.DisplayWrongCalculationDialog -> {
            modalNavController.present(DialogConfigurations.alert) { dialogKey: String ->
                NotificationDialog(
                    text = MR.strings.add_transaction_incorrect_calculation,
                    onOkClick = {
                        modalNavController.popBackStack(dialogKey, animate = false)
                    }
                )
            }
        }
    }
}