package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.database.mappers.categoryToDomainModel
import com.finance_tracker.finance_tracker.domain.models.Category
import com.financetracker.financetracker.data.CategoriesEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoriesRepository(
    private val categoriesEntityQueries: CategoriesEntityQueries
) {
    suspend fun insertCategory(
        categoryName: String,
        categoryIcon: String,
        isExpense: Boolean,
        isIncome: Boolean,
    ) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.insertCategory(
                id = null,
                name = categoryName,
                icon = categoryIcon,
                position = null,
                isExpense = isExpense,
                isIncome = isIncome,
            )
        }
    }

    suspend fun deleteCategoryById(id: Long) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.deleteCategoryById(id)
        }
    }

    suspend fun updateCategoryPosition(categoryFrom: Long, categoryTo: Long) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.replaceCategory(categoryFrom, categoryTo)
            categoriesEntityQueries.replaceCategory(categoryTo, categoryFrom)
        }
    }

    suspend fun getAllExpenseCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            categoriesEntityQueries.getAllExpenseCategories().executeAsList()
                .map { it.categoryToDomainModel() }
        }
    }

    suspend fun getAllIncomeCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            categoriesEntityQueries.getAllIncomeCategories().executeAsList()
                .map { it.categoryToDomainModel() }
        }
    }

    fun getCategoriesCountFlow(): Flow<Int> {
        return categoriesEntityQueries.getCategoriesCount()
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toInt() ?: 0 }
    }

    suspend fun updateCategory(id: Long, name: String, iconId: String) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.updateAccountById(name = name, icon = iconId, id = id)
        }
    }
}