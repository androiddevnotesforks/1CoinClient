package com.finance_tracker.finance_tracker.core.common

import android.content.Context

actual object ApplicationContextStorage {
    @Suppress("LateinitUsage")
    lateinit var applicationContext: Context
}