package com.finance_tracker.finance_tracker.data.database.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual class DriverFactoryModule {
    actual fun create(): Module = module {
        // TODO: iOS. DriverFactoryModule
        //singleOf(::DriverFactory)
    }
}