package com.finance_tracker.finance_tracker.features.export_import.import.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics

class ImportAnalytics: BaseAnalytics() {

    override val screenName = "ImportDialog"

    fun trackCancelClick() {
        trackClick(eventName = "Cancel")
    }
}