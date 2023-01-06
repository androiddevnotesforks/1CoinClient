package com.finance_tracker.finance_tracker.presentation.detail_account.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction

class DetailAccountAnalytics: BaseAnalytics() {

    override val screenName = "DetailAccountScreen"

    fun trackBackClick() {
        trackClick(eventName = "Back")
    }

    fun trackEditAccountClick(account: Account) {
        trackClick(
            eventName = "EditAccount",
            properties = mapOf(
                "name" to account.name,
                "type" to account.type.analyticsName,
            )
        )
    }

    fun trackTransactionClick(transaction: Transaction) {
        trackClick(
            eventName = "Transaction",
            properties = mapOf(
                "account_name" to transaction.account.name,
                "type" to transaction.type.analyticsName
            )
        )
    }
}