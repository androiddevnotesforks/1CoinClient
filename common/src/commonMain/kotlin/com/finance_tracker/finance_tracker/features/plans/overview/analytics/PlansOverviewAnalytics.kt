package com.finance_tracker.finance_tracker.features.plans.overview.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics

class PlansOverviewAnalytics: BaseAnalytics() {

    override val screenName = "PlansOverviewScreen"

    fun trackAddLimitClick() {
        trackClick(eventName = "AddLimit")
    }
}