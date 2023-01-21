package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.suspendTransaction
import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Category
import kotlinx.coroutines.flow.Flow

class CategoriesInteractor(
    private val categoriesRepository: CategoriesRepository,
    private val transactionsRepository: TransactionsRepository,
    private val appDatabase: AppDatabase
) {

    suspend fun insertCategory(
        categoryName: String,
        categoryIcon: String,
        isExpense: Boolean,
        isIncome: Boolean,
    ) {
        categoriesRepository.insertCategory(categoryName, categoryIcon, isExpense, isIncome)
    }

    fun getCategoriesCountFlow(): Flow<Int> {
        return categoriesRepository.getCategoriesCountFlow()
    }

    suspend fun deleteCategoryById(id: Long) {
        appDatabase.suspendTransaction {
            transactionsRepository.deleteCategoryForTransactionsByCategoryId(id)
            categoriesRepository.deleteCategoryById(id)
        }
    }

    suspend fun getAllIncomeCategories(): List<Category> {
        return categoriesRepository.getAllIncomeCategories()
    }

    suspend fun getAllExpenseCategories(): List<Category> {
        return categoriesRepository.getAllExpenseCategories()
    }

    suspend fun updateCategoryPosition(categoryFrom: Long, categoryTo: Long) {
        return categoriesRepository.updateCategoryPosition(categoryFrom, categoryTo)
    }


    suspend fun updateCategory(id: Long, name: String, iconId: String) {
        categoriesRepository.updateCategory(id, name, iconId)
    }
}