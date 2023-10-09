package com.finance_tracker.finance_tracker.data.repositories.export_import

expect class FileReader {

    suspend fun readLines(uri: String): List<String>

    suspend fun readText(uri: String): String

    suspend fun unzipFiles(zipFilePath: String, destinationDirectoryPath: String): Map<String, String>
}