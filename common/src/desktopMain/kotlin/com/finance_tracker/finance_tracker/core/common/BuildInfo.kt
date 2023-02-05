package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.BuildKonfig

actual object BuildInfo {
    actual val isDebug: Boolean
        get() = BuildKonfig.isDebug
    actual val buildType: BuildType
        get() = if (isDebug) {
            BuildType.Debug
        } else {
            BuildType.Release
        }
}