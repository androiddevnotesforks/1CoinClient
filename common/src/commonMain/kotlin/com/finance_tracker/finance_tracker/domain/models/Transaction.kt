package com.finance_tracker.finance_tracker.domain.models

import java.util.Date

data class Transaction(
    val id: Long? = null,
    val type: TransactionType,
    val account: Account,
    val amount: Amount = Amount.default,
    val category: Category? = null,
    val date: Date
) {
    companion object {
        val EMPTY = Transaction(
            id = null,
            type = TransactionType.Expense,
            account = Account.EMPTY,
            amount = Amount.default,
            category = null,
            date = Date()
        )
    }
}