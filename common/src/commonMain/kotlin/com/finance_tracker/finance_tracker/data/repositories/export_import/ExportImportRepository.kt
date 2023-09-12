package com.finance_tracker.finance_tracker.data.repositories.export_import

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.csv.fromCsvFile
import com.finance_tracker.finance_tracker.core.common.csv.toCsv
import com.finance_tracker.finance_tracker.core.common.date.toShortFormatWithMilliseconds
import com.finance_tracker.finance_tracker.data.database.helpers.AccountsEntityRetriever
import com.finance_tracker.finance_tracker.data.database.helpers.CategoriesEntityRetriever
import com.finance_tracker.finance_tracker.data.database.helpers.TransactionsEntityRetriever
import com.finance_tracker.finance_tracker.data.database.helpers.insertRow
import com.financetracker.financetracker.data.AccountsEntityQueries
import com.financetracker.financetracker.data.CategoriesEntityQueries
import com.financetracker.financetracker.data.DbVersionEntityQueries
import com.financetracker.financetracker.data.TransactionsEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime

class ExportImportRepository(
    private val fileReader: FileReader,
    private val fileWriter: FileWriter,
    private val transactionsEntityQueries: TransactionsEntityQueries,
    private val accountsEntityQueries: AccountsEntityQueries,
    private val categoriesEntityQueries: CategoriesEntityQueries,
    private val dbVersionEntityQueries: DbVersionEntityQueries,
    private val database: AppDatabase
) {

    suspend fun import(
        categoriesFilePath: String,
        accountsFilePath: String,
        transactionsFilePath: String
    ) {
        withContext(Dispatchers.IO) {
            val categories = getColumnFieldValues(categoriesFilePath)
            val accounts = getColumnFieldValues(accountsFilePath)
            val transactions = getColumnFieldValues(transactionsFilePath)

            database.transaction {
                categoriesEntityQueries.deleteAll()
                categories.forEach(categoriesEntityQueries::insertRow)
                accountsEntityQueries.deleteAll()
                accounts.forEach(accountsEntityQueries::insertRow)
                transactionsEntityQueries.deleteAll()
                transactions.forEach(transactionsEntityQueries::insertRow)
            }
        }
    }

    suspend fun unzipFiles(zipFilePath: String): Map<String, String> {
        return fileReader.unzipFiles(
            zipFilePath = zipFilePath,
            destinationDirectoryPath = "import"
        )
    }

    suspend fun checkDbVersion() {
        withContext(Dispatchers.IO) {
            val dbVersion = dbVersionEntityQueries.getVersion().executeAsOne()
            if (dbVersion != LastSupportedDbVersion) {
                error("dbVersion ($dbVersion) != LastSupportedDbVersion ($LastSupportedDbVersion)")
            }
        }
    }

    private suspend fun getColumnFieldValues(filePath: String?): List<List<String?>> {
        return fromCsvFile(fileReader, filePath!!)
    }

    suspend fun readContent(filePath: String?): String {
        return fileReader
            .readText(filePath!!)
    }

    suspend fun getAllTransactionsCsvContent(): String {
        return withContext(Dispatchers.IO) {
            val transactions = transactionsEntityQueries.getAllTransactions().executeAsList()
            (listOf(TransactionsEntityRetriever.columns()) +
                    transactions.map { TransactionsEntityRetriever.fieldValues(it) })
                .toCsv()
        }
    }

    suspend fun getAllAccountsCsvContent(): String {
        return withContext(Dispatchers.IO) {
            val accounts = accountsEntityQueries.getAllAccounts().executeAsList()
            (listOf(AccountsEntityRetriever.columns()) +
                    accounts.map { AccountsEntityRetriever.fieldValues(it) })
                .toCsv()
        }
    }

    suspend fun getAllCategoriesCsvContent(): String {
        return withContext(Dispatchers.IO) {
            val categories = categoriesEntityQueries.getAllCategories().executeAsList()
            (listOf(CategoriesEntityRetriever.columns()) +
                    categories.map { CategoriesEntityRetriever.fieldValues(it) })
                .toCsv()
        }
    }

    suspend fun getDbVersion(): Long {
        return withContext(Dispatchers.IO) {
            dbVersionEntityQueries.getVersion().executeAsOne()
        }
    }

    suspend fun export(
        directoryUri: String,
        dateTime: LocalDateTime,
        files: List<FileData>
    ): String {
        return withContext(Dispatchers.IO) {
            fileWriter.zipFiles(
                directoryUri = directoryUri,
                files = files,
                name = "1coin_${dateTime.toShortFormatWithMilliseconds()}_dump"
            )
        }
    }

    companion object {
        private const val LastSupportedDbVersion = 9L
    }
}