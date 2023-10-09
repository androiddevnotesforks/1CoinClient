package com.finance_tracker.finance_tracker.presentation.models

import com.finance_tracker.finance_tracker.domain.models.Amount
import kotlinx.serialization.Serializable

@Serializable
data class AmountSerializable(
    val currency: CurrencySerializable,
    val amountValue: Double
)

fun Amount.toSerializable(): AmountSerializable {
    return AmountSerializable(
        currency = currency.toSerializable(),
        amountValue = amountValue
    )
}

fun AmountSerializable.toDomain(): Amount {
    return Amount(
        currency = currency.toDomain(),
        amountValue = amountValue
    )
}