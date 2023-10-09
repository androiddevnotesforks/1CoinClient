package com.finance_tracker.finance_tracker.features.transactions

sealed interface TransactionsAction {
    data object RefreshTransactions: TransactionsAction
}