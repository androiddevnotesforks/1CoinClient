package com.finance_tracker.finance_tracker.core.analytics

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

// TODO -- implement Amplitude analytics for iOS like AndroidAnalyticsTracker.kt
@Suppress("UnusedPrivateMember")
class IosAnalyticsTracker(
    analyticsSettings: AnalyticsSettings
) : AnalyticsTracker, CoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.IO

    @Suppress("NotImplementedDeclaration")
    override fun init(context: Context, userId: String) {

    }

    @Suppress("NotImplementedDeclaration")
    override fun setUserProperty(property: String, value: Any) {

    }

    @Suppress("NotImplementedDeclaration")
    override fun track(event: AnalyticsEvent) {

    }
}