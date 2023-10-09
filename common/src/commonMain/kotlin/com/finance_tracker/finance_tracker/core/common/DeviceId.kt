package com.finance_tracker.finance_tracker.core.common

expect class UniqueDeviceIdProvider {
    fun getUniqueDeviceId(): String
}
