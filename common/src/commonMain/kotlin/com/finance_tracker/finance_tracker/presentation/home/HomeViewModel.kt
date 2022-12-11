package com.finance_tracker.finance_tracker.presentation.home

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.core.common.EventChannel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class HomeViewModel(
    private val accountsRepository: AccountsRepository,
    private val currenciesInteractor: CurrenciesInteractor,
    transactionsInteractor: TransactionsInteractor,
): KViewModel() {

    private val currencyRatesFlow = currenciesInteractor.getCurrencyRatesFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = mapOf())

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    val lastTransactions = transactionsInteractor.getLastTransactions()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList())

    private val _totalBalance = MutableStateFlow(Amount.default)
    val totalBalance = _totalBalance.asStateFlow()

    private val _events = EventChannel<HomeEvent>()
    val events = _events.receiveAsFlow()

    private var loadTotalAmountJob: Job? = null

    private val totalCurrencyFlow = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = Currency.default)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Napier.e("HomeViewModel", throwable)
    }

    init {
        updateCurrencyRates()
    }

    fun onScreenComposed() {
        loadAccounts()
        loadTotalAmount()
    }

    private fun updateCurrencyRates() {
        viewModelScope.launch {
            currenciesInteractor.updateCurrencyRates()
        }
    }

    private fun loadAccounts() {
        val oldAccountsCount = _accounts.value.size
        viewModelScope.launch {
            _accounts.value  = accountsRepository.getAllAccountsFromDatabase()
            val newAccountsCount = _accounts.value.size
            if (oldAccountsCount in 1 until newAccountsCount) {
                _events.send(HomeEvent.ScrollToItemAccounts(newAccountsCount - 1))
            }
        }
    }

    private fun loadTotalAmount() {
        loadTotalAmountJob?.cancel()
        loadTotalAmountJob = currencyRatesFlow.combine(totalCurrencyFlow) {
                currencyRates, baseCurrency ->
            val balanceInBaseCurrency = getTotalBalanceAmountInCurrency(
                currencyRates = currencyRates,
                currency = baseCurrency
            )
            _totalBalance.value = Amount(
                currency = baseCurrency,
                amountValue = balanceInBaseCurrency
            )
        }
            .launchIn(viewModelScope + exceptionHandler)
    }

    private suspend fun getTotalBalanceAmountInCurrency(
        currencyRates: CurrencyRates,
        currency: Currency
    ): Double {
        return accountsRepository.getAllAccountsFromDatabase()
            .sumOf { account ->
                account.balance.convertToCurrency(
                    currencyRates = currencyRates,
                    toCurrency = currency
                )
            }
    }
}