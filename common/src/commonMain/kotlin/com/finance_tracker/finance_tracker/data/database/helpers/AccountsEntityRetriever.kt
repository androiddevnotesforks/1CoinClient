package com.finance_tracker.finance_tracker.data.database.helpers

import com.finance_tracker.finance_tracker.domain.models.Account
import com.financetracker.financetracker.data.AccountsEntity
import com.financetracker.financetracker.data.AccountsEntityQueries

object AccountsEntityRetriever: EntityDataRetriever<AccountsEntity> {

    override fun fieldValues(entity: AccountsEntity): List<String> {
        return with(entity) {
            listOf(
                id, type, name, balance, colorId, currency, position
            ).map { it.toString() }
        }
    }

    override fun columns(): List<String> {
        return listOf(
            "id", "type", "name", "balance", "colorId", "currency", "position"
        )
    }
}

@Suppress("MagicNumber")
fun AccountsEntityQueries.insertRow(row: List<String?>) {
    insertAccount(
        id = row[0]?.toLong(),
        type = Account.Type.fromName(row[1]!!),
        name = row[2]!!,
        balance = row[3]!!.toDouble(),
        colorId = row[4]!!.toInt(),
        currency = row[5]!!,
        position = row[6]!!.toInt(),
    )
}