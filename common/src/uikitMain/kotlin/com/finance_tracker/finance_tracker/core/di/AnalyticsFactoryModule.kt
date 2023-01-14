package com.finance_tracker.finance_tracker.core.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual class AnalyticsFactoryModule actual constructor() {
    actual fun create(): Module = module {
        // TODO: iOS. AnalyticsFactoryModule
        // singleOf<AnalyticsTracker, AnalyticsSettings>(::DesktopAnalyticsTracker)
    }
}