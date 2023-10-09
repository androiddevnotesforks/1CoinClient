package com.finance_tracker.finance_tracker.core.common

import platform.UIKit.UIDevice

actual class UniqueDeviceIdProvider {
    actual fun getUniqueDeviceId(): String {
        return UIDevice.currentDevice.identifierForVendor.toString()
    }
}