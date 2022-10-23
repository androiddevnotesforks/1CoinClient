package com.finance_tracker.finance_tracker.presentation.categories

import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import com.finance_tracker.finance_tracker.domain.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.finance_tracker.finance_tracker.core.common.ViewModel

class CategorySettingsScreenViewModel(
    private val repository: CategoriesRepository
): ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        loadAllCategories()
    }

    private fun loadAllCategories() {
        viewModelScope.launch {
            _categories.value = repository.getAllCategories()
        }
    }

    fun swapSections(from: Int, to: Int) {
        val fromItem = _categories.value[from]
        val toItem = _categories.value[to]
        val newList = _categories.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem

        _categories.value = newList
    }

}