package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.common.BuildConfig

actual object BuildInfo {
    actual val isDebug: Boolean
        get() = BuildConfig.DEBUG

    actual val buildType: BuildType
        get() = BuildType.byName(BuildConfig.BUILD_TYPE)
}