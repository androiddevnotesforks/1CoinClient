package com.finance_tracker.finance_tracker.features.analytics.delegates

import com.finance_tracker.finance_tracker.domain.models.TransactionType
import kotlinx.coroutines.cancel

class AnalyticsDelegates(
    val trendsAnalyticsDelegate: TrendsAnalyticsDelegate,
    val monthTxsByCategoryDelegate: MonthTxsByCategoryDelegate
) {
    fun updateMonthTxsByCategory() {
        monthTxsByCategoryDelegate.updateMonthTxsByCategory()
    }

    fun setTransactionType(transactionType: TransactionType) {
        monthTxsByCategoryDelegate.onTransactionTypeSelect(transactionType)
        monthTxsByCategoryDelegate.setSelectedTransactionType(transactionType)
        trendsAnalyticsDelegate.setSelectedTransactionType(transactionType)
    }

    fun cancel() {
        trendsAnalyticsDelegate.cancel()
        monthTxsByCategoryDelegate.cancel()
    }
}