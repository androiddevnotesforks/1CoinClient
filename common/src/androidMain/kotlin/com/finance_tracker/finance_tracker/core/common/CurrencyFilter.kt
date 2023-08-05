package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.domain.models.Currency
import java.util.Locale
import java.util.Currency as JavaCurrency

fun List<Currency>.filterByQuery(
    query: String,
    language: String
): List<Currency> {
    return filter { currency ->
        currency.code.contains(query, ignoreCase = true) ||
                currency.symbol.contains(query, ignoreCase = true) ||
                JavaCurrency.getInstance(currency.code).getDisplayName(Locale(language))
                    .contains(query, ignoreCase = true)
    }
}