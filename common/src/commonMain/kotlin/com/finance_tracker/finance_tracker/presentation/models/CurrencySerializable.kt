package com.finance_tracker.finance_tracker.presentation.models

import com.finance_tracker.finance_tracker.domain.models.Currency
import kotlinx.serialization.Serializable

@Serializable
data class CurrencySerializable(
    val code: String,
    val symbol: String
)

fun Currency.toSerializable(): CurrencySerializable {
    return CurrencySerializable(
        code = code,
        symbol = symbol
    )
}

fun CurrencySerializable.toDomain(): Currency {
    return Currency(
        code = code,
        symbol = symbol
    )
}