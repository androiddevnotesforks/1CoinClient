package com.finance_tracker.finance_tracker.data.models

import java.util.Date

data class Transaction(
    val type: Type,
    val amountCurrency: String,
    val amount: Double = 0.0,
    val category: String? = null,
    val cardNumber: String? = null,
    val date: Date? = null
) {
    enum class Type {
        Expense,
        Income,
        Transfer
    }
}