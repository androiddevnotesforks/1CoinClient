package com.finance_tracker.finance_tracker.data.models

import androidx.compose.ui.graphics.Color

data class Account(
    val type: Type,
    val name: String,
    val color: Color
) {
    enum class Type {
        DebitCard,
        CreditCard,
        Cash
    }
}