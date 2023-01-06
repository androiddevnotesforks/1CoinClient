package com.finance_tracker.finance_tracker.core.analytics

import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.common.Logger
import com.finance_tracker.finance_tracker.core.common.Context

class AndroidAnalyticsTracker: AnalyticsTracker {

    @Suppress("LateinitUsage")
    private lateinit var amplitude: Amplitude

    override fun init(context: Context) {
        amplitude = Amplitude(
            Configuration(
                apiKey = AnalyticsTracker.AMPLITUDE_API_KEY,
                context = context
            )
        ).apply {
            setUserId(AnalyticsTracker.ANONYM_USER_ID)
            logger.logMode = Logger.LogMode.DEBUG
        }
    }

    override fun setUserId(userId: String) {
        amplitude.setUserId(userId)
    }

    override fun track(event: AnalyticsEvent) {
        amplitude.track(event.name, event.properties)
        log(event)
    }

    private fun log(event: AnalyticsEvent) {
        amplitude.logger.debug("${event.name}. properties: ${event.properties}")
    }
}