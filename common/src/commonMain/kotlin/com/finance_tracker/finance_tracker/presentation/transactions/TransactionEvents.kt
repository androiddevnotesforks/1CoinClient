package com.finance_tracker.finance_tracker.presentation.transactions

sealed interface TransactionEvents {
    object RefreshTransactions: TransactionEvents
}