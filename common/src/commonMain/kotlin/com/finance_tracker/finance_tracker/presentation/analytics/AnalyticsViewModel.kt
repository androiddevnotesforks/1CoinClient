package com.finance_tracker.finance_tracker.presentation.analytics

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.core.common.date.currentMonth
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Month

class AnalyticsViewModel(
    private val transactionsInteractor: TransactionsInteractor
): KViewModel() {

    private val _monthTransactionsByCategory = MutableStateFlow<TxsByCategoryChart?>(null)
    val monthTransactionsByCategory = _monthTransactionsByCategory.asStateFlow()

    private val _selectedMonth = MutableStateFlow(Clock.System.currentMonth())
    val selectedMonth = _selectedMonth.asStateFlow()

    private val _transactionTypeTab = MutableStateFlow(TransactionTypeTab.Expense)
    val transactionTypeTab = _transactionTypeTab.asStateFlow()

    private val _isLoadingMonthTxsByCategory = MutableStateFlow(false)
    val isLoadingMonthTxsByCategory = _isLoadingMonthTxsByCategory.asStateFlow()

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
        loadMonthTxsByCategoryJob = viewModelScope.launch {
            _isLoadingMonthTxsByCategory.value = true
            _monthTransactionsByCategory.value = transactionsInteractor.getTransactions(
                transactionType = transactionTypeTab.toTransactionType(),
                month = month
            )
            _isLoadingMonthTxsByCategory.value = false
        }
    }
}