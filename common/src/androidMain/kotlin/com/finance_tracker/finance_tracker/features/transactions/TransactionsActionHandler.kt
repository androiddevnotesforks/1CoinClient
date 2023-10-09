package com.finance_tracker.finance_tracker.features.transactions

import com.finance_tracker.finance_tracker.core.common.pagination.LazyPagingItems
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel

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