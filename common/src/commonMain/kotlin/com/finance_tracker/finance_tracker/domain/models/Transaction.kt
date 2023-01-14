package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.core.common.date.currentLocalDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime

data class Transaction(
    val id: Long? = null,
    val type: TransactionType,
    val account: Account,
    val amount: Amount = Amount.default,
    val category: Category? = null,
    val dateTime: LocalDateTime,
    val insertionDateTime: LocalDateTime?,
) {
    companion object {
        val EMPTY = Transaction(
            id = null,
            type = TransactionType.Expense,
            account = Account.EMPTY,
            amount = Amount.default,
            category = null,
            dateTime = Clock.System.currentLocalDateTime(),
            insertionDateTime = null
        )
    }
}