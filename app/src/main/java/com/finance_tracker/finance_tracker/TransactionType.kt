package com.finance_tracker.finance_tracker

import com.finance_tracker.finance_tracker.data.models.Transaction

enum class TransactionType {
    Expense,
    Income,
    Transfer
}

fun Transaction.Type.toTransactionTypeEntity(): TransactionType {
    return when (this) {
        Transaction.Type.Expense -> TransactionType.Expense
        Transaction.Type.Income -> TransactionType.Income
        Transaction.Type.Transfer -> TransactionType.Transfer
    }
}