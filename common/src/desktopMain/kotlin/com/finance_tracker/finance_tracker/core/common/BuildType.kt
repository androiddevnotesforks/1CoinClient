package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.BuildKonfig

actual object BuildType {
    actual val isDebug: Boolean
        get() = BuildKonfig.isDebug
}