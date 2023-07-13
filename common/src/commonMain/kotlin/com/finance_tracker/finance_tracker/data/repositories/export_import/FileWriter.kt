package com.finance_tracker.finance_tracker.data.repositories.export_import

import com.finance_tracker.finance_tracker.core.common.Context

expect class FileWriter(context: Context) {

    suspend fun zipFiles(
        directoryUri: String,
        name: String,
        files: List<FileData>
    ): String
}