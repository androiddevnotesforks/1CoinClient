package com.finance_tracker.finance_tracker.data.repositories.export_import

actual class FileWriter {
    
    actual suspend fun zipFiles(
        directoryUri: String,
        name: String,
        files: List<FileData>
    ): String {
        // TODO: write
        return ""
    }
}