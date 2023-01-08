package com.finance_tracker.finance_tracker.core.analytics

import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.common.Logger
import com.amplitude.core.events.Identify
import com.finance_tracker.finance_tracker.common.BuildConfig
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
            logger.logMode = if (BuildConfig.DEBUG) {
                Logger.LogMode.DEBUG
            } else {
                Logger.LogMode.OFF
            }
        }
    }

    override fun setUserProperty(property: String, value: Any) {
        amplitude.identify(Identify().apply { set(property, value) })
        logUserProperties(property, value)
    }

    override fun setUserId(userId: String) {
        amplitude.setUserId(userId)
    }

    override fun track(event: AnalyticsEvent) {
        amplitude.track(event.name, event.properties)
        log(event)
    }

    private fun log(event: AnalyticsEvent) {
        var message = "${event.name}. "
        if (event.properties.isNotEmpty()) {
            message += "properties: ${event.properties}"
        }
        amplitude.logger.debug(message)
    }

    private fun logUserProperties(property: String, value: Any) {
        amplitude.logger.debug("UserProperties: {$property=$value}")
    }
}