package com.finance_tracker.finance_tracker.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance_tracker.finance_tracker.data.repositories.CategoryRepository
import com.finance_tracker.finance_tracker.domain.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CategorySettingsScreenViewModel(
    private val repository: CategoryRepository
): ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        getCategoriesFromDatabase()
    }

    private fun getCategoriesFromDatabase() {
        viewModelScope.launch {
            _categories.value = repository.getAllCategories()
        }
    }
}