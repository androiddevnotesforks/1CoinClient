package com.finance_tracker.finance_tracker.core.common.di

import com.finance_tracker.finance_tracker.core.common.logger.KoinLogger
import com.finance_tracker.finance_tracker.core.di.allKoinModules
import org.koin.core.context.startKoin

actual object Di {

    fun init() {
        startKoin {
            logger(logger = KoinLogger())
            modules(allKoinModules())
        }
    }
}