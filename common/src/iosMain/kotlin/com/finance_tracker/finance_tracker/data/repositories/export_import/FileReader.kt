package com.finance_tracker.finance_tracker.data.repositories.export_import

import com.finance_tracker.finance_tracker.core.common.Context

actual class FileReader actual constructor(
    val context: Context
) {
    actual suspend fun readLines(uri: String): List<String> {
        // TODO: Read file on iOS
        return emptyList()
    }
}