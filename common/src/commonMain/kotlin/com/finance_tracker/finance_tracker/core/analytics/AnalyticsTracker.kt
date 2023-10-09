package com.finance_tracker.finance_tracker.core.analytics

interface AnalyticsTracker {

    fun init(userId: String)

    fun track(event: AnalyticsEvent)

    fun setUserProperty(property: String, value: Any)

    companion object {
        const val ANONYM_USER_ID = "anonym"
    }
}
