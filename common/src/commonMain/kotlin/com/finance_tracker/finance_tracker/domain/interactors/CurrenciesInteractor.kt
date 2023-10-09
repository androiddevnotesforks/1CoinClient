package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.suspendTransaction
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class CurrenciesInteractor(
    private val appDatabase: AppDatabase,
    private val currenciesRepository: CurrenciesRepository,
    private val accountsRepository: AccountsRepository
) {

    fun getCurrencyRatesFlow(): Flow<CurrencyRates> {
        return currenciesRepository.getCurrencyRatesFlow()
    }

    suspend fun updateCurrencyRates() {
        currenciesRepository.updateCurrencyRates()
    }

    suspend fun savePrimaryCurrency(currency: Currency) {
        currenciesRepository.savePrimaryCurrency(currency)
    }

    suspend fun presetPrimaryCurrency(currency: Currency) {
        appDatabase.suspendTransaction {
            accountsRepository.addDefaultAccount(currency)
            currenciesRepository.savePrimaryCurrency(currency)
        }
    }

    fun getPrimaryCurrencyFlow(): Flow<Currency> {
        return currenciesRepository.getPrimaryCurrencyFlow()
    }

    suspend fun getPrimaryCurrency(): Currency {
        return currenciesRepository.getPrimaryCurrency()
    }

    suspend fun isPrimaryCurrencySelected(): Boolean {
        return currenciesRepository.isPrimaryCurrencySelected()
    }

    fun isPrimaryCurrencySelectedSync(): Boolean {
        return runBlocking {
            currenciesRepository.isPrimaryCurrencySelected()
        }
    }
}