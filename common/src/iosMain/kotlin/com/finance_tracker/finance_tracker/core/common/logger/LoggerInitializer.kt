package com.finance_tracker.finance_tracker.core.common.logger

import com.finance_tracker.finance_tracker.core.common.BuildInfo
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual class LoggerInitializer {
    actual fun init() {
        if (BuildInfo.isDebug) Napier.base(DebugAntilog())
    }
}