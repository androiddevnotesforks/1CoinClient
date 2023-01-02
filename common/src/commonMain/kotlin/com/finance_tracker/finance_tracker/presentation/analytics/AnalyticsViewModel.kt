package com.finance_tracker.finance_tracker.presentation.analytics

import com.finance_tracker.finance_tracker.core.common.date.currentMonth
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import com.finance_tracker.finance_tracker.presentation.analytics.delegates.TrendsAnalyticsDelegate
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
    currenciesInteractor: CurrenciesInteractor,
    val trendsAnalyticsDelegate: TrendsAnalyticsDelegate
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

    private val primaryCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .shareIn(viewModelScope, started = SharingStarted.Lazily, replay = 1)

    /*private val incomeTrendsWeekTotal = MutableStateFlow(100)
    private val incomeTrendsWeekTrend = MutableStateFlow(listOf(1, 2, 3))
    private val expenseTrendsWeekTotal = MutableStateFlow(100)
    private val expenseTrendsWeekTrend = MutableStateFlow(listOf(1, 2, 3))

    private val incomeTrendsMonthTotal = MutableStateFlow(100)
    private val incomeTrendsMonthTrend = MutableStateFlow(listOf(1, 2, 3))
    private val expenseTrendsMonthTotal = MutableStateFlow(100)
    private val expenseTrendsMonthTrend = MutableStateFlow(listOf(1, 2, 3))

    private val incomeTrendsYearTotal = MutableStateFlow(100)
    private val incomeTrendsYearTrend = MutableStateFlow(listOf(1, 2, 3))
    private val expenseTrendsYearTotal = MutableStateFlow(100)
    private val expenseTrendsYearTrend = MutableStateFlow(listOf(1, 2, 3))*/

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