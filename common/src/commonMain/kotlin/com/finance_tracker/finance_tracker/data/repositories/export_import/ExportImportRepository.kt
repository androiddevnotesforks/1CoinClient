package com.finance_tracker.finance_tracker.data.repositories.export_import

class ExportImportRepository(
    private val fileReader: FileReader
) {

    suspend fun import(uri: String): List<String> {
        return fileReader.readLines(uri)
    }
}