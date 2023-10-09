package com.finance_tracker.finance_tracker.core.common

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

actual class UniqueDeviceIdProvider(
    private val applicationContext: Context
) {
    @SuppressLint("HardwareIds")
    actual fun getUniqueDeviceId(): String {
        return Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
    }
}