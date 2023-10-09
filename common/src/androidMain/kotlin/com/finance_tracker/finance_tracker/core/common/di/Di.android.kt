package com.finance_tracker.finance_tracker.core.common.di

import android.content.Context
import com.finance_tracker.finance_tracker.core.di.allKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

actual object Di {

    fun init(context: Context) {
        startKoin {
            androidLogger()
            androidContext(context)
            modules(allKoinModules())
        }
    }
}