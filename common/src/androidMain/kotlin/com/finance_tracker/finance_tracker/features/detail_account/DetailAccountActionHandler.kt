package com.finance_tracker.finance_tracker.features.detail_account

import com.finance_tracker.finance_tracker.core.common.pagination.LazyPagingItems
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel

fun handleAction(
    action: DetailAccountAction,
    lazyTransactionList: LazyPagingItems<TransactionListModel>
) {
    when (action) {
        DetailAccountAction.RefreshTransactions -> {
            lazyTransactionList.refresh()
        }
    }
}