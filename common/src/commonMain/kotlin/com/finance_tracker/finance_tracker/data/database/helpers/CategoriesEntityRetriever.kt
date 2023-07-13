package com.finance_tracker.finance_tracker.data.database.helpers

import com.financetracker.financetracker.data.CategoriesEntity
import com.financetracker.financetracker.data.CategoriesEntityQueries

object CategoriesEntityRetriever: EntityDataRetriever<CategoriesEntity> {
    override fun fieldValues(entity: CategoriesEntity): List<String> {
        return with(entity) {
            listOf(
                id, name, icon, position, isExpense, isIncome
            ).map { it.toString() }
        }
    }

    override fun columns(): List<String> {
        return listOf(
            "id", "name", "icon", "position", "isExpense", "isIncome"
        )
    }
}

@Suppress("MagicNumber")
fun CategoriesEntityQueries.insertRow(row: List<String?>) {
    insertCategory(
        id = row[0]?.toLong(),
        name = row[1]!!,
        icon = row[2]!!,
        position = row[3]?.toLong(),
        isExpense = row[4]?.toBoolean() ?: false,
        isIncome = row[5]?.toBoolean() ?: false
    )
}