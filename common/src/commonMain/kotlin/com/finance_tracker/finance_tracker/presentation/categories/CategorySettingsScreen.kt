package com.finance_tracker.finance_tracker.presentation.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.*
import com.finance_tracker.finance_tracker.core.ui.CategoryCard
import com.finance_tracker.finance_tracker.core.ui.ExpenseIncomeTabs
import com.finance_tracker.finance_tracker.core.ui.ItemWrapper
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun CategorySettingsScreen(
    viewModel: CategorySettingsViewModel = getViewModel()
) {

    val categories by viewModel.categories.collectAsState()
    val rootController = LocalRootController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        CategorySettingsAppBar()

        ExpenseIncomeTabs(
            modifier = Modifier
                .padding(
                    top = 24.dp,
                    start = 20.dp,
                    bottom = 4.dp,
                ),
            onCategorySelect = {
                if(it.textId == "add_transaction_tab_income") {
                    rootController.findRootController().push(ExpenseIncomeNavigationTree.Income.name)
                } else {
                    rootController.findRootController().push(ExpenseIncomeNavigationTree.Expense.name)

                }
            }
        )

        LazyDragColumn(
            items = categories,
            onSwap = viewModel::swapCategories,
            contentPaddingValues = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            )
        ) { index, category ->
            ItemWrapper(
                isFirstItem = index == 0,
                isLastItem = index == categories.lastIndex,
                modifier = Modifier
            ) {
                CategoryCard(
                    data = category,
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp,
                            end = 16.dp
                        ),
                    onCrossDeleteClick = { viewModel.deleteCategory(category.id) }
                )
            }
        }
    }
}