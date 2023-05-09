package com.finance_tracker.finance_tracker.domain.models

data class Budget(
    val spentAmount: Amount,
    val limitAmount: Amount,
    val category: Category,
)