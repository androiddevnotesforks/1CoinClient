package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import com.finance_tracker.finance_tracker.domain.models.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private val amountExtCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
private val currenciesRepository: CurrenciesRepository by lazy { getKoin().get() }
private val currencyRatesFlow = currenciesRepository.getCurrencyRatesFlow()
    .stateIn(amountExtCoroutineScope, initialValue = emptyMap())

fun Transaction.convertToCurrencyAmountTransaction(
    toCurrency: Currency,
    currencyRates: CurrencyRates = currencyRatesFlow.value
): Transaction {
    return copy(
        primaryAmount = primaryAmount.convertToCurrencyAmount(
            toCurrency = toCurrency,
            currencyRates = currencyRates
        ),
        secondaryAmount = secondaryAmount?.convertToCurrencyAmount(
            toCurrency = toCurrency,
            currencyRates = currencyRates
        )
    )
}