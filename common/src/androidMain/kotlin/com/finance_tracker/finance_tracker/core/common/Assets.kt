package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.MR

actual fun getRaw(context: Context, name: String, ext: String): String {
    return MR.files.base_rates.readText(context)
}