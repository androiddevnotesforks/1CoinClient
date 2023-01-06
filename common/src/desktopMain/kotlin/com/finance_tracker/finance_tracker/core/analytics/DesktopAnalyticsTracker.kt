package com.finance_tracker.finance_tracker.core.analytics

import com.amplitude.Amplitude
import com.amplitude.AmplitudeLog
import com.amplitude.Event
import com.finance_tracker.finance_tracker.core.common.DesktopContext
import com.finance_tracker.finance_tracker.core.common.runSafeCatching
import io.github.aakira.napier.Napier
import org.json.JSONObject

class DesktopAnalyticsTracker: AnalyticsTracker {

    private val amplitude = Amplitude.getInstance()
    private var userId = AnalyticsTracker.ANONYM_USER_ID

    override fun init(context: DesktopContext) {
        amplitude.init(AnalyticsTracker.AMPLITUDE_API_KEY)
        amplitude.setLogMode(AmplitudeLog.LogMode.DEBUG)
    }

    override fun setUserId(userId: String) {
        this.userId = userId
    }

    override fun track(event: AnalyticsEvent) {
        val desktopEvent = Event(event.name, userId)

        val eventProps = JSONObject()
        runSafeCatching {
            event.properties.forEach(eventProps::put)
        }

        desktopEvent.eventProperties = eventProps
        desktopEvent.platform  = "Desktop"

        amplitude.logEvent(desktopEvent)
        log(event)
    }

    private fun log(event: AnalyticsEvent) {
        Napier.d(message = "${event.name}. properties: ${event.properties}", tag = "Amplitude")
    }
}