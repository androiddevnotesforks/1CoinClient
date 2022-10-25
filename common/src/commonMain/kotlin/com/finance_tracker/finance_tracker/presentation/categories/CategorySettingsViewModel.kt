package com.finance_tracker.finance_tracker.presentation.categories

import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import com.finance_tracker.finance_tracker.domain.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.finance_tracker.finance_tracker.core.common.ViewModel

class CategorySettingsViewModel(
    private val repository: CategoriesRepository
): ViewModel() {

    private val _expenseCategories = MutableStateFlow<List<Category>>(emptyList())
    val expenseCategories = _expenseCategories.asStateFlow()

    private val _incomeCategories = MutableStateFlow<List<Category>>(emptyList())
    val incomeCategories = _incomeCategories.asStateFlow()

    init {
        loadAllExpenseCategories()
    }

    private fun loadAllExpenseCategories() {
        viewModelScope.launch {
            _expenseCategories.value = repository.getAllExpenseCategories()
        }
    }

    private fun loadAllIncomeCategories() {
        viewModelScope.launch {
            _incomeCategories.value = repository.getAllIncomeCategories()
        }
    }


    fun swapExpenseCategories(from: Int, to: Int) {
        val fromItem = _expenseCategories.value[from]
        val toItem = _expenseCategories.value[to]
        val newList = _expenseCategories.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem

        _expenseCategories.value = newList
        saveListState(fromItem, toItem)
    }

    fun swapIncomeCategories(from: Int, to: Int) {
        val fromItem = _incomeCategories.value[from]
        val toItem = _incomeCategories.value[to]
        val newList = _incomeCategories.value.toMutableList()

        newList[from] = toItem
        newList[to] = fromItem

        _incomeCategories.value = newList
        saveListState(fromItem, toItem)
    }

    private fun saveListState(categoryFrom: Category, categoryTo: Category) {
        viewModelScope.launch {
            repository.updateCategoryPosition(categoryFrom, categoryTo)
        }
    }

    fun deleteCategory(id: Long) {
        viewModelScope.launch {
            repository.deleteCategoryById(id)
            loadAllExpenseCategories()
        }
    }

}