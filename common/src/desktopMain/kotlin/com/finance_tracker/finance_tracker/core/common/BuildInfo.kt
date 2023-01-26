package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.BuildKonfig

actual object BuildInfo {
    actual val isDebug: Boolean
        get() = BuildKonfig.isDebug
    actual val appVersion: String
        get() = BuildKonfig.appVersion
}