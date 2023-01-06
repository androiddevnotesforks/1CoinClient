package com.finance_tracker.finance_tracker.presentation.add_category

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import com.finance_tracker.finance_tracker.presentation.add_category.analytics.AddCategoryAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCategoryViewModel(
    private val repository: CategoriesRepository,
    private val addCategoryAnalytics: AddCategoryAnalytics,
    private val transactionTypeTab: TransactionTypeTab
): BaseViewModel<AddCategoryAction>() {

    private val _icons = MutableStateFlow(List(63) { "ic_category_${it + 1}" })
    val icons = _icons.asStateFlow()

    private val _chosenIcon = MutableStateFlow(icons.value.first())
    val chosenIcon = _chosenIcon.asStateFlow()

    private val _categoryName = MutableStateFlow("")
    val categoryName = _categoryName.asStateFlow()

    init {
        addCategoryAnalytics.trackScreenOpen()
    }

    fun onIconChoose(icon: String) {
        _chosenIcon.value = icon
    }

    private fun addExpenseCategory(categoryName: String, categoryIcon: String) {
        viewModelScope.launch {
            repository.insertCategory(
                categoryName = categoryName,
                categoryIcon = categoryIcon,
                isExpense = true,
                isIncome = false
            )
        }
    }

    private fun addIncomeCategory(categoryName: String, categoryIcon: String) {
        viewModelScope.launch {
            repository.insertCategory(
                categoryName = categoryName,
                categoryIcon = categoryIcon,
                isExpense = false,
                isIncome = true
            )
        }
    }

    fun setCategoryName(name: String) {
        _categoryName.value = name
    }

    fun onBackClick() {
        addCategoryAnalytics.onBackClick()
        viewAction = AddCategoryAction.CloseScreen
    }

    fun addCategory() {
        val newCategoryName = categoryName.value
        addCategoryAnalytics.onAddCategoryClick(
            transactionTypeTab = transactionTypeTab,
            iconName = chosenIcon.value,
            categoryName = newCategoryName
        )

        if (newCategoryName.isBlank()) return

        when (transactionTypeTab) {
            TransactionTypeTab.Expense -> {
                addExpenseCategory(
                    categoryName = newCategoryName,
                    categoryIcon = chosenIcon.value
                )
                viewAction = AddCategoryAction.CloseScreen
            }
            TransactionTypeTab.Income -> {
                addIncomeCategory(
                    categoryName = newCategoryName,
                    categoryIcon = chosenIcon.value
                )
                viewAction = AddCategoryAction.CloseScreen
            }
        }
    }
}