package com.finance_tracker.finance_tracker.data.repositories.export_import

import com.finance_tracker.finance_tracker.core.common.Context

actual class FileReader actual constructor(
    val context: Context
) {
    actual suspend fun readLines(uri: String): List<String> {
        // TODO: Read file on iOS
        return emptyList()
    }

    actual suspend fun readText(uri: String): String {
        // TODO: Read file on iOS
        return ""
    }

    actual suspend fun unzipFiles(zipFilePath: String, destinationDirectoryPath: String): Map<String, String> {
        // TODO: Unzip file on iOS
        return emptyMap()
    }
}