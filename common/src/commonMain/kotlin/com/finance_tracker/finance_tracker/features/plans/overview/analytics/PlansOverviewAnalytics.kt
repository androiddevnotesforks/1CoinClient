package com.finance_tracker.finance_tracker.features.plans.overview.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.Plan

class PlansOverviewAnalytics: BaseAnalytics() {

    override val screenName = "PlansOverviewScreen"

    fun trackAddLimitClick() {
        trackClick(eventName = "AddLimit")
    }

    fun trackLimitClick(plan: Plan) {
        trackClick(
            eventName = "Limit",
            properties = mapOf("category_name" to plan.category.name)
        )
    }
}