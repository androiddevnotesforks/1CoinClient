package com.finance_tracker.finance_tracker.core.common.logger

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual class LoggerInitializer {

    actual fun init() {
        Napier.base(DebugAntilog())
    }
}