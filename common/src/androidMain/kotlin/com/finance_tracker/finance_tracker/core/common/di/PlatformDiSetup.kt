package com.finance_tracker.finance_tracker.core.common.di

import com.finance_tracker.finance_tracker.core.common.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication

actual fun KoinApplication.platformKoinSetup(context: Context) {
    androidLogger()
    androidContext(context)
}