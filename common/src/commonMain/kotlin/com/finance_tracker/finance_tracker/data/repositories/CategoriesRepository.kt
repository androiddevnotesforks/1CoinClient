package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.toCategoryString
import com.finance_tracker.finance_tracker.data.database.mappers.categoryToDomainModel
import com.finance_tracker.finance_tracker.domain.models.Category
import com.financetracker.financetracker.data.CategoriesEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.getImageByFileName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoriesRepository(
    private val categoriesEntityQueries: CategoriesEntityQueries,
    val context: Context,
) {
    suspend fun insertCategory(
        categoryName: String,
        categoryIcon: ImageResource,
        isExpense: Boolean,
        isIncome: Boolean,
    ) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.insertCategory(
                id = null,
                name = categoryName,
                icon = categoryIcon.toCategoryString(),
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

    suspend fun updateCategoryPosition(categoryId1: Long, newPosition1: Int, categoryId2: Long, newPosition2: Int) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.transaction {
                categoriesEntityQueries.replaceCategory(
                    position = newPosition1.toLong(),
                    id = categoryId1
                )
                categoriesEntityQueries.replaceCategory(
                    position = newPosition2.toLong(),
                    id = categoryId2
                )
            }
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

    suspend fun updateCategory(id: Long, name: String, iconId: ImageResource) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.updateAccountById(name = name, icon = iconId.toCategoryString(), id = id)
        }
    }

    suspend fun getCategoryIcon(iconName: String): ImageResource? {
        return withContext(Dispatchers.IO) {
            MR.images.getImageByFileName(iconName)
        }
    }
}