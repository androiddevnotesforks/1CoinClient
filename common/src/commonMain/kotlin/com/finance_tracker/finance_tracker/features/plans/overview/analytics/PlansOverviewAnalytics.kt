package com.finance_tracker.finance_tracker.features.plans.overview.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
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

    fun trackNextPeriodClick(yearMonth: YearMonth) {
        trackClick(
            eventName = "NextPeriod",
            properties = mapOf(
                "current_period" to "${yearMonth.year}-${yearMonth.month}"
            )
        )
    }

    fun trackPreviousPeriodClick(yearMonth: YearMonth) {
        trackClick(
            eventName = "PreviousPeriod",
            properties = mapOf(
                "current_period" to "${yearMonth.year}-${yearMonth.month}"
            )
        )
    }

    fun trackSetLimitClick(yearMonth: YearMonth) {
        trackClick(
            eventName = "SetLimit",
            properties = mapOf(
                "current_period" to "${yearMonth.year}-${yearMonth.month}"
            )
        )
    }

    fun trackTotalMonthLimitClick(yearMonth: YearMonth) {
        trackClick(
            eventName = "SemTotalMonthLimit",
            properties = mapOf(
                "current_period" to "${yearMonth.year}-${yearMonth.month}"
            )
        )
    }
}