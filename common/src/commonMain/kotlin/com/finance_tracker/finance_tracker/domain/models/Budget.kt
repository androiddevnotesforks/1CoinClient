package com.finance_tracker.finance_tracker.domain.models

data class Budget(
    val spentAmount: Double,
    val limitAmount: Double,
    val category: Category,
)