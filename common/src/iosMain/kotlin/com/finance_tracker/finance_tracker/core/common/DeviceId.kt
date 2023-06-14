package com.finance_tracker.finance_tracker.core.common

import platform.UIKit.UIDevice

actual fun getUniqueDeviceId(context: Context): String {
    return UIDevice.currentDevice.identifierForVendor.toString()
}