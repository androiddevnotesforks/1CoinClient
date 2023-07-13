package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.core.common.date.currentLocalDateTime
import com.finance_tracker.finance_tracker.data.repositories.export_import.ExportImportRepository
import com.finance_tracker.finance_tracker.data.repositories.export_import.FileData
import com.finance_tracker.finance_tracker.domain.exceptions.DeprecatedBackupFileException
import com.finance_tracker.finance_tracker.domain.models.ExportMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ExportImportInteractor(
    private val exportImportRepository: ExportImportRepository,
    private val json: Json
) {

    suspend fun import(uri: String) {
        return withContext(Dispatchers.IO) {
            val unzippedFilePaths = exportImportRepository.unzipFiles(uri)

            val metadataJson = exportImportRepository.readContent(unzippedFilePaths["metadata.json"])
            val metadata = json.decodeFromString<ExportMetadata>(metadataJson)
            val currentDbVersion = exportImportRepository.getDbVersion()
            if (currentDbVersion != metadata.dbVersion) {
                throw DeprecatedBackupFileException()
            }

            exportImportRepository.import(
                categoriesFilePath = unzippedFilePaths.getValue("categories.csv"),
                accountsFilePath = unzippedFilePaths.getValue("accounts.csv"),
                transactionsFilePath = unzippedFilePaths.getValue("transactions.csv")
            )
        }
    }

    suspend fun export(directoryUri: String): String {
        val exportMetadata = ExportMetadata(
            dbVersion = exportImportRepository.getDbVersion()
        )
        return exportImportRepository.export(
            directoryUri = directoryUri,
            dateTime = Clock.System.currentLocalDateTime(),
            files = listOf(
                FileData(
                    content = json.encodeToString(exportMetadata),
                    name = "metadata",
                    ext = "json"
                ),
                FileData(
                    content = exportImportRepository.getAllTransactionsCsvContent(),
                    name = "transactions",
                    ext = "csv"
                ),
                FileData(
                    content = exportImportRepository.getAllAccountsCsvContent(),
                    name = "accounts",
                    ext = "csv"
                ),
                FileData(
                    content = exportImportRepository.getAllCategoriesCsvContent(),
                    name = "categories",
                    ext = "csv"
                )
            )
        )
    }
}