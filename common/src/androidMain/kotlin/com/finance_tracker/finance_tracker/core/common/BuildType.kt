package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.common.BuildConfig

actual object BuildType {
    actual val isDebug: Boolean
        get() = BuildConfig.DEBUG
}