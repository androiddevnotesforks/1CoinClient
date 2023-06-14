package com.finance_tracker.finance_tracker.core.common.logger

import io.github.aakira.napier.Napier
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

internal class KoinLogger(level: Level = Level.INFO) : Logger(level) {
    override fun display(level: Level, msg: MESSAGE) {
        when (level) {
            Level.DEBUG -> Napier.d(message = msg)
            Level.INFO -> Napier.i(message = msg)
            Level.WARNING -> Napier.w(message = msg)
            Level.ERROR -> Napier.e(message = msg)
            else -> Napier.e(message = msg)
        }
    }
}
