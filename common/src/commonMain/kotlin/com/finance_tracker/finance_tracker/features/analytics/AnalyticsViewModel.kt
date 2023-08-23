package com.finance_tracker.finance_tracker.features.analytics

import com.finance_tracker.finance_tracker.core.common.getKoin
import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypesMode
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.features.analytics.analytics.AnalyticsScreenAnalytics
import com.finance_tracker.finance_tracker.features.analytics.delegates.AnalyticsDelegates
import com.finance_tracker.finance_tracker.features.analytics.delegates.MonthTxsByCategoryDelegate
import com.finance_tracker.finance_tracker.features.analytics.delegates.TrendsAnalyticsDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class AnalyticsViewModel(
    analyticsScreenAnalytics: AnalyticsScreenAnalytics,
    currenciesInteractor: CurrenciesInteractor
): BaseViewModel<Nothing>() {

    private val transactionTypes = TransactionTypesMode.Main.types
    private val _transactionTypeTab = MutableStateFlow(transactionTypes.first())
    val transactionTypeTab = _transactionTypeTab.asStateFlow()
    val transactionTypeTabPage = transactionTypeTab
        .map { transactionTypes.indexOf(it) }
        .stateIn(viewModelScope, initialValue = 0)
    val transactionTypesCount = transactionTypes.size

    private val typesToAnalyticsDelegates = transactionTypes.associateWith {
        getKoin().get<AnalyticsDelegates>().apply {
            setTransactionType(it.toTransactionType())
        }
    }

    val primaryCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, initialValue = Currency.default)

    init {
        analyticsScreenAnalytics.trackScreenOpen()
    }

    fun onScreenComposed() {
        typesToAnalyticsDelegates.values.forEach { it.updateMonthTxsByCategory() }
    }

    fun onTransactionTypeSelect(transactionTypeTab: TransactionTypeTab) {
        _transactionTypeTab.value = transactionTypeTab
    }

    fun getMonthTxsByCategoryDelegate(page: Int): MonthTxsByCategoryDelegate {
        return typesToAnalyticsDelegates[getTransactionTypeByPage(page)]?.monthTxsByCategoryDelegate
            ?: error("No MonthTxsByCategoryDelegate for page $page")
    }

    fun getTransactionTypeTab(page: Int): TransactionTypeTab {
        return TransactionTypesMode.Main.types.getOrNull(page)
            ?: error("No TransactionTypeTab for page $page")
    }

    fun getTrendsAnalyticsDelegate(page: Int): TrendsAnalyticsDelegate {
        return typesToAnalyticsDelegates[getTransactionTypeByPage(page)]?.trendsAnalyticsDelegate
            ?: error("No TrendsAnalyticsDelegate for page $page")
    }

    fun onPageChanged(page: Int) {
        if (page in transactionTypes.indices) {
            _transactionTypeTab.value = getTransactionTypeByPage(page)
        }
    }

    private fun getTransactionTypeByPage(page: Int): TransactionTypeTab {
        return transactionTypes.getOrNull(page) ?: error("No transaction type for page $page")
    }

    override fun onCleared() {
        super.onCleared()
        typesToAnalyticsDelegates.values.forEach { it.cancel() }
    }
}