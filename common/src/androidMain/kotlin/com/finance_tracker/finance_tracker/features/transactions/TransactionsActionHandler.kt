package com.finance_tracker.finance_tracker.features.transactions

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.pagination.LazyPagingItems
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.ui.dialogs.DeleteAlertDialog
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.features.add_transaction.AddTransactionScreenParams
import dev.icerock.moko.resources.compose.stringResource
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push

fun handleAction(
    action: TransactionsAction,
    baseLocalsStorage: BaseLocalsStorage,
    lazyTransactionList: LazyPagingItems<TransactionListModel>,
    onUnselectItems: () -> Unit,
    onCancelClick: (dialogKey: String) -> Unit,
    onConfirmDeleteClick: (dialogKey: String) -> Unit
) {
    val navController = baseLocalsStorage.rootController.findRootController()
    val modalController = navController.findModalController()
    when (action) {
        TransactionsAction.RefreshTransactions -> {
            lazyTransactionList.refresh()
        }
        is TransactionsAction.OpenTransactionDetailScreen -> {
            navController.push(
                screen = MainNavigationTree.AddTransaction.name,
                params = AddTransactionScreenParams(
                    transaction = action.transaction
                )
            )
        }
        is TransactionsAction.CloseDeleteTransactionDialog -> {
            modalController.popBackStack(action.dialogKey, animate = false)
        }
        TransactionsAction.UnselectAllItems -> {
            onUnselectItems()
        }
        is TransactionsAction.ShowDeleteDialog -> {
            modalController.present(DialogConfigurations.alert) { key ->
                DeleteAlertDialog(
                    titleEntity = if (action.selectedItemsCount > 1) {
                        stringResource(MR.strings.deleting_transactions)
                    } else {
                        stringResource(MR.strings.deleting_transaction)
                    },
                    onCancelClick = { onCancelClick(key) },
                    onDeleteClick = { onConfirmDeleteClick(key) }
                )
            }
        }
        TransactionsAction.OpenAddTransactionScreen -> {
            navController.push(
                screen = MainNavigationTree.AddTransaction.name
            )
        }
    }
}