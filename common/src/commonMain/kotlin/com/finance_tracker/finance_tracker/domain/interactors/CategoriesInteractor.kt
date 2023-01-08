package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import kotlinx.coroutines.flow.Flow

class CategoriesInteractor(
    private val categoriesRepository: CategoriesRepository
) {

    fun getCategoriesCountFlow(): Flow<Int> {
        return categoriesRepository.getCategoriesCountFlow()
    }
}