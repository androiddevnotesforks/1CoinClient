package com.finance_tracker.finance_tracker.core.common

// AppBuildConfig is created because IDE don't see BuildInfo for autocomplete
object AppBuildConfig {
    val DEBUG = BuildInfo.isDebug
    val AMPLITUDE_API_KEY = if (BuildInfo.isDebug) {
        "6236b52f5efeb4208d20f85cc8697971"
    } else {
        "53a89fb85c5895ac9fb42631eacd28c2"
    }
    val APP_VERSION = BuildInfo.appVersion
}