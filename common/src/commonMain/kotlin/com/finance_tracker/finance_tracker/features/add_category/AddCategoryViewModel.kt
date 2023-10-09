package com.finance_tracker.finance_tracker.features.add_category

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.asImageDescResource
import com.finance_tracker.finance_tracker.core.common.view_models.ComponentViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CategoriesInteractor
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.features.add_category.analytics.AddCategoryAnalytics
import dev.icerock.moko.resources.desc.image.ImageDescResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val MaxCategoryNameLength = 28

class AddCategoryViewModel(
    private val categoriesInteractor: CategoriesInteractor,
    private val addCategoryAnalytics: AddCategoryAnalytics,
    private val screenParams: AddCategoryScreenParams
): ComponentViewModel<AddCategoryAction, AddCategoryComponent.Action>() {

    private val categoriesNamesList = List(63) { "ic_category_${it + 1}" }

    private val _icons = MutableStateFlow<List<ImageDescResource>>(emptyList())
    val icons = _icons.asStateFlow()

    val category = screenParams.category
    private val _chosenIcon = MutableStateFlow(
        category?.icon ?: MR.images.ic_category_1.asImageDescResource()
    )
    val chosenIcon = _chosenIcon.asStateFlow()

    private val _categoryName = MutableStateFlow(category?.name.orEmpty())
    val categoryName = _categoryName.asStateFlow()

    val transactionType = screenParams.transactionType
    val isEditMode = category != null

    init {
        addCategoryAnalytics.trackScreenOpen()
        initCategories(categoriesNamesList)
    }

    private fun initCategories(iconsNames: List<String>) {
        viewModelScope.launch {
            iconsNames.forEach {
                val categoryIcon = categoriesInteractor.getCategoryIcon(it)

                if (categoryIcon != null) {
                    _icons.value = _icons.value + categoryIcon
                }
            }
        }
    }

    fun onIconChoose(icon: ImageDescResource) {
        _chosenIcon.value = icon
    }

    private suspend fun addCategory(
        categoryName: String,
        categoryIcon: ImageDescResource,
        transactionType: TransactionType
    ) {
        categoriesInteractor.insertCategory(
            categoryName = categoryName,
            categoryIcon = categoryIcon,
            isExpense = transactionType == TransactionType.Expense,
            isIncome = transactionType == TransactionType.Income
        )
    }

    fun setCategoryName(name: String) {
        if (name.length <= MaxCategoryNameLength) {
            _categoryName.value = name
        }
    }

    fun onBackClick() {
        addCategoryAnalytics.onBackClick()
        componentAction = AddCategoryComponent.Action.CloseScreen
    }

    fun addOrUpdateCategory() {
        val newCategoryName = categoryName.value
        addCategoryAnalytics.onAddOrEditCategoryClick(
            transactionType = transactionType,
            iconName = chosenIcon.value,
            categoryName = newCategoryName,
            isEdit = isEditMode,
        )

        if (newCategoryName.isBlank()) return

        viewModelScope.launch {
            if (isEditMode) {
                val categoryId = category?.id ?: return@launch
                categoriesInteractor.updateCategory(
                    id = categoryId,
                    name = newCategoryName,
                    iconId = chosenIcon.value
                )
            } else {
                addCategory(
                    categoryName = newCategoryName,
                    categoryIcon = chosenIcon.value,
                    transactionType = transactionType
                )
            }
            componentAction = AddCategoryComponent.Action.CloseScreen
        }
    }
}