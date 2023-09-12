package com.finance_tracker.finance_tracker.domain.models

enum class TransactionType(
    val analyticsName: String
) {
    Expense(analyticsName = "Expense"),
    Income(analyticsName = "Income"),
    Transfer(analyticsName = "Transfer");

    companion object {
        fun fromName(name: String): TransactionType {
            return when (name) {
                "Expense" -> Expense
                "Income" -> Income
                "Transfer" -> Transfer
                else -> error("No TransactionType for name $name")
            }
        }
    }
}