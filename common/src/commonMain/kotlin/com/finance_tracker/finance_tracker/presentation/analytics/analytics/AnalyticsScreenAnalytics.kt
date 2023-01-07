package com.finance_tracker.finance_tracker.presentation.analytics.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.analytics.peroid_bar_chart.PeriodChip
import kotlinx.datetime.Month

class AnalyticsScreenAnalytics: BaseAnalytics() {

    override val screenName = "AnalyticsScreen"

    fun trackTrendEvent(
        transactionType: TransactionType,
        periodChip: PeriodChip
    ) {
        trackEvent(
            eventName = "TrendView",
            properties = mapOf(
                "type" to transactionType.analyticsName,
                "period" to periodChip.analyticsName
            )
        )
    }

    fun trackTxsByCategory(
        transactionType: TransactionType,
        month: Month
    ) {
        trackEvent(
            eventName = "TxsByCategoryView",
            properties = mapOf(
                "type" to transactionType.analyticsName,
                "month" to month.name.lowercase()
            )
        )
    }
}