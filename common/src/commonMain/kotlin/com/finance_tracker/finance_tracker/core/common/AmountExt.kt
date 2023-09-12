package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private val amountExtCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
private val currenciesRepository: CurrenciesRepository by lazy { getKoin().get() }
private val currencyRatesFlow = currenciesRepository.getCurrencyRatesFlow()
    .stateIn(amountExtCoroutineScope, initialValue = emptyMap())

@Throws(IllegalStateException::class)
fun Amount.convertToCurrencyValue(
    toCurrency: Currency,
    currencyRates: CurrencyRates = currencyRatesFlow.value,
): Double {
    return if (currency == toCurrency) {
        amountValue
    } else {
        val fromCurrencyRates = currencyRates[currency.code]
            ?: return 0.0
        val toCurrencyRates = currencyRates[toCurrency.code]
            ?: return 0.0

        return amountValue / fromCurrencyRates.rate * toCurrencyRates.rate
    }
}

fun Amount.convertToCurrencyAmount(
    toCurrency: Currency,
    currencyRates: CurrencyRates = currencyRatesFlow.value
): Amount {
    return Amount(
        amountValue = convertToCurrencyValue(
            currencyRates = currencyRates,
            toCurrency = toCurrency
        ),
        currency = toCurrency
    )
}

operator fun Amount.minus(amount: Amount): Amount {
    val value = if (currency == amount.currency) {
        amountValue - amount.amountValue
    } else {
        amountValue - amount.convertToCurrencyValue(toCurrency = currency)
    }
    return Amount(
        currency = currency,
        amountValue = value
    )
}


operator fun Amount.plus(amount: Amount): Amount {
    val value = if (currency == amount.currency) {
        amountValue + amount.amountValue
    } else {
        amountValue + amount.convertToCurrencyValue(toCurrency = currency)
    }
    return Amount(
        currency = currency,
        amountValue = value
    )
}

fun Amount.Companion.zero(currency: Currency): Amount {
    return Amount(
        currency = currency,
        amountValue = 0.0
    )
}

inline fun <T> Iterable<T>.sumOf(currency: Currency, selector: (T) -> Amount): Amount {
    var sum = Amount(
        currency = currency,
        amountValue = 0.0
    )
    for (element in this) {
        sum += selector(element)
    }
    return sum
}