package com.finance_tracker.finance_tracker.data.repositories.export_import

expect class FileWriter {

    suspend fun zipFiles(
        directoryUri: String,
        name: String,
        files: List<FileData>
    ): String
}