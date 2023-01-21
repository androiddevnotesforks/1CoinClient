package com.finance_tracker.finance_tracker.core.common

object AppBuildConfig {
    val DEBUG = BuildType.isDebug
    val AMPLITUDE_API_KEY = if (BuildType.isDebug) {
        "6236b52f5efeb4208d20f85cc8697971"
    } else {
        "53a89fb85c5895ac9fb42631eacd28c2"
    }
}