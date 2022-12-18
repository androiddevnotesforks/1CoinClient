package com.finance_tracker.finance_tracker.presentation.transactions

import com.finance_tracker.finance_tracker.core.common.pagination.LazyPagingItems
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel

sealed interface TransactionsAction {
    object RefreshTransactions: TransactionsAction
}

fun handleAction(
    action: TransactionsAction,
    lazyTransactionList: LazyPagingItems<TransactionListModel>
) {
    when (action) {
        TransactionsAction.RefreshTransactions -> {
            lazyTransactionList.refresh()
        }
    }
}