package com.finance_tracker.finance_tracker.data.database.helpers

import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.financetracker.financetracker.data.GetAllTransactions
import com.financetracker.financetracker.data.TransactionsEntityQueries
import kotlinx.datetime.toLocalDateTime

object TransactionsEntityRetriever: EntityDataRetriever<GetAllTransactions> {

    override fun fieldValues(entity: GetAllTransactions): List<String> {
        return with(entity) {
            listOf(
                id, type, amount, amountCurrency, categoryId, accountId, insertionDate,
                date, secondaryAmount, secondaryAmountCurrency, secondaryAccountId
            ).map { it.toString() }
        }
    }

    override fun columns(): List<String> {
        return listOf(
            "id", "type", "amount", "amountCurrency", "categoryId", "accountId", "insertionDate",
            "date", "secondaryAmount", "secondaryAmountCurrency", "secondaryAccountId"
        )
    }
}

@Suppress("MagicNumber")
fun TransactionsEntityQueries.insertRow(row: List<String?>) {
    insertTransaction(
        id = row[0]?.toLong(),
        type = TransactionType.fromName(row[1]!!),
        amount = row[2]!!.toDouble(),
        amountCurrency = row[3]!!,
        categoryId = row[4]?.toLong(),
        accountId = row[5]?.toLong(),
        insertionDate = row[6]!!.toLocalDateTime(),
        date = row[7]!!.toLocalDateTime(),
        secondaryAmount = row[8]?.toDouble(),
        secondaryAmountCurrency = row[9],
        secondaryAccountId = row[10]?.toLong()
    )
}