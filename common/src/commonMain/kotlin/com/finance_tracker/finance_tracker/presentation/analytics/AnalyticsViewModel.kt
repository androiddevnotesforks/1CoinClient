package com.finance_tracker.finance_tracker.presentation.analytics

import com.finance_tracker.finance_tracker.core.common.date.currentMonth
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlinx.datetime.Month

class AnalyticsViewModel(
    private val transactionsInteractor: TransactionsInteractor,
    currenciesInteractor: CurrenciesInteractor
): BaseViewModel<Nothing>() {

    private val currencyRatesFlow = currenciesInteractor.getCurrencyRatesFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = mapOf())

    private val _monthTransactionsByCategory = MutableStateFlow<TxsByCategoryChart?>(null)
    val monthTransactionsByCategory = _monthTransactionsByCategory.asStateFlow()

    private val _selectedMonth = MutableStateFlow(Clock.System.currentMonth())
    val selectedMonth = _selectedMonth.asStateFlow()

    private val _transactionTypeTab = MutableStateFlow(TransactionTypeTab.Expense)
    val transactionTypeTab = _transactionTypeTab.asStateFlow()

    private val _isLoadingMonthTxsByCategory = MutableStateFlow(false)
    val isLoadingMonthTxsByCategory = _isLoadingMonthTxsByCategory.asStateFlow()

    val primaryCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .shareIn(viewModelScope, started = SharingStarted.Lazily, replay = 1)

    private var loadMonthTxsByCategoryJob: Job? = null

    fun onScreenComposed() {
        updateMonthTxsByCategory()
    }

    private fun updateMonthTxsByCategory() {
        loadMonthTxsByCategory(
            transactionTypeTab = transactionTypeTab.value,
            month = selectedMonth.value
        )
    }

    fun onMonthSelect(month: Month) {
        _selectedMonth.value = month
        loadMonthTxsByCategory(
            transactionTypeTab = transactionTypeTab.value,
            month = month
        )
    }

    fun onTransactionTypeSelect(transactionTypeTab: TransactionTypeTab) {
        _transactionTypeTab.value = transactionTypeTab
        loadMonthTxsByCategory(
            transactionTypeTab = transactionTypeTab,
            month = selectedMonth.value
        )
    }

    private fun loadMonthTxsByCategory(transactionTypeTab: TransactionTypeTab, month: Month) {
        loadMonthTxsByCategoryJob?.cancel()
        loadMonthTxsByCategoryJob = primaryCurrency.combine(currencyRatesFlow) { currency, currencyRates ->
            _isLoadingMonthTxsByCategory.value = true
            _monthTransactionsByCategory.value = transactionsInteractor.getTransactions(
                transactionType = transactionTypeTab.toTransactionType(),
                month = month,
                primaryCurrency = currency,
                currencyRates = currencyRates
            )
            _isLoadingMonthTxsByCategory.value = false
        }.launchIn(viewModelScope)
    }
}