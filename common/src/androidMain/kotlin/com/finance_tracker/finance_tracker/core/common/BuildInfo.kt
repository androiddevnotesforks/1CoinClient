package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.BuildKonfig
import com.finance_tracker.finance_tracker.common.BuildConfig

actual object BuildInfo {
    actual val isDebug: Boolean
        get() = BuildConfig.DEBUG

    @Suppress("KotlinConstantConditions")
    actual val appVersion: String
        get() {
            val buildType = BuildConfig.BUILD_TYPE
            val suffix = if (buildType == "release") "" else "-$buildType"
            return BuildKonfig.appVersion + suffix
        }
}