package com.finance_tracker.finance_tracker.presentation.analytics

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.presentation.analytics.analytics.AnalyticsScreenAnalytics
import com.finance_tracker.finance_tracker.presentation.analytics.delegates.MonthTxsByCategoryDelegate
import com.finance_tracker.finance_tracker.presentation.analytics.delegates.TrendsAnalyticsDelegate
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Month

class AnalyticsViewModel(
    val trendsAnalyticsDelegate: TrendsAnalyticsDelegate,
    val monthTxsByCategoryDelegate: MonthTxsByCategoryDelegate,
    analyticsScreenAnalytics: AnalyticsScreenAnalytics
): BaseViewModel<Nothing>() {

    private val _transactionTypeTab = MutableStateFlow(TransactionTypeTab.Expense)
    val transactionTypeTab = _transactionTypeTab.asStateFlow()

    init {
        analyticsScreenAnalytics.trackScreenOpen()
    }

    fun onScreenComposed() {
        monthTxsByCategoryDelegate.updateMonthTxsByCategory()
    }

    fun onMonthSelect(month: Month) {
        monthTxsByCategoryDelegate.onMonthSelect(month)
    }

    fun onTransactionTypeSelect(transactionTypeTab: TransactionTypeTab) {
        _transactionTypeTab.value = transactionTypeTab
        monthTxsByCategoryDelegate.onTransactionTypeSelect(transactionTypeTab.toTransactionType())
    }

    override fun onCleared() {
        super.onCleared()
        trendsAnalyticsDelegate.cancel()
        monthTxsByCategoryDelegate.cancel()
    }
}