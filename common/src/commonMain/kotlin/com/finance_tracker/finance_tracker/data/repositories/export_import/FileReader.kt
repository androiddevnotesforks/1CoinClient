package com.finance_tracker.finance_tracker.data.repositories.export_import

import com.finance_tracker.finance_tracker.core.common.Context

expect class FileReader(context: Context) {
    suspend fun readLines(uri: String): List<String>
}