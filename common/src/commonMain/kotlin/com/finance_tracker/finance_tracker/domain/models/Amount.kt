package com.finance_tracker.finance_tracker.domain.models

data class Amount(
    val currency: Currency,
    val amountValue: Double
) {

    companion object {

        val default = Amount(
            currency = Currency.default,
            amountValue = 0.0
        )

        fun default(currency: Currency): Amount {
            return Amount(
                currency = currency,
                amountValue = 0.0
            )
        }
    }
}