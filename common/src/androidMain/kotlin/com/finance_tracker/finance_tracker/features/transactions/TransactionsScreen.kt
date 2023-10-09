package com.finance_tracker.finance_tracker.features.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.pagination.collectAsLazyPagingItems
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.core.ui.decompose_ext.subscribeBottomDialog
import com.finance_tracker.finance_tracker.core.ui.dialogs.DeleteBottomDialog
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList
import com.finance_tracker.finance_tracker.features.transactions.views.TransactionsAppBar

@Composable
internal fun TransactionsScreen(
    component: TransactionsComponent
) {
    ComposeScreen(
        component = component,
        withBottomNavigation = true
    ) {
        val viewModel = component.viewModel
        val paginatedTransactions = remember(Unit) { viewModel.getPaginatedTransactions() }
        val lazyTransactionList = paginatedTransactions.collectAsLazyPagingItems()

        val selectedItems by viewModel.selectedItems.collectAsState()

        viewModel.watchViewActions { action, _ ->
            handleAction(
                action = action,
                lazyTransactionList = lazyTransactionList
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val selectedItemsCount by remember {
                derivedStateOf { selectedItems.count() }
            }

            TransactionsAppBar(
                selectedItemsCount = selectedItemsCount,
                onCloseClick = viewModel::onCloseAppBarMenuClick,
                onDeleteClick = { viewModel.onDeleteTransactionsClick(selectedItemsCount) }
            )

            val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
            CommonTransactionsList(
                transactions = lazyTransactionList,
                contentPadding = PaddingValues(
                    bottom = CoinPaddings.bottomNavigationBar + navigationBarsHeight
                ),
                onClick = { transactionData ->
                    if (selectedItemsCount > 0) {
                        viewModel.onSelectItem(transactionData)
                    } else {
                        viewModel.onTransactionClick(transactionData.transaction)
                    }
                },
                onLongClick = {
                    viewModel.onSelectItem(it)
                },
                onAddTransactionClick = viewModel::onAddTransactionClick
            )
        }

        component.bottomDialogSlot.subscribeBottomDialog(
            onDismissRequest = viewModel::onDismissDialog
        ) { child ->
            when (child) {
                is TransactionsComponent.BottomDialogChild.DeleteDialog -> {
                    DeleteBottomDialog(
                        component = child.component,
                        onCancelClick = viewModel::onCancelDeletingTransactionsClick,
                        onDeleteClick = viewModel::onConfirmDeleteTransactionsClick
                    )
                }
            }
        }
    }
}