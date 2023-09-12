package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.suspendTransaction
import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import com.finance_tracker.finance_tracker.data.repositories.PlansRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Category
import dev.icerock.moko.resources.ImageResource
import kotlinx.coroutines.flow.Flow

class CategoriesInteractor(
    private val categoriesRepository: CategoriesRepository,
    private val transactionsRepository: TransactionsRepository,
    private val plansRepository: PlansRepository,
    private val appDatabase: AppDatabase
) {

    suspend fun insertCategory(
        categoryName: String,
        categoryIcon: ImageResource,
        isExpense: Boolean,
        isIncome: Boolean,
    ) {
        categoriesRepository.insertCategory(categoryName, categoryIcon, isExpense, isIncome)
    }

    fun getCategoriesCountFlow(): Flow<Int> {
        return categoriesRepository.getCategoriesCountFlow()
    }

    suspend fun getCategoryIcon(iconName: String): ImageResource? {
        return categoriesRepository.getCategoryIcon(iconName)
    }

    suspend fun deleteCategoryById(id: Long) {
        appDatabase.suspendTransaction {
            transactionsRepository.deleteCategoryForTransactionsByCategoryId(id)
            categoriesRepository.deleteCategoryById(id)
            plansRepository.deletePlansByCategoryId(id)
        }
    }

    suspend fun getAllIncomeCategories(): List<Category> {
        return categoriesRepository.getAllIncomeCategories()
    }

    suspend fun getAllExpenseCategories(): List<Category> {
        return categoriesRepository.getAllExpenseCategories()
    }

    suspend fun updateCategoryPosition(
        categoryId1: Long, newPosition1: Int,
        categoryId2: Long, newPosition2: Int
    ) {
        return categoriesRepository.updateCategoryPosition(
            categoryId1, newPosition1,
            categoryId2, newPosition2
        )
    }


    suspend fun updateCategory(id: Long, name: String, iconId: ImageResource) {
        categoriesRepository.updateCategory(id, name, iconId)
    }
}