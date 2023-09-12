package com.finance_tracker.finance_tracker.domain.models

data class Plan(
    val id: Long? = null,
    val category: Category,
    val spentAmount: Amount,
    val limitAmount: Amount
)