package com.finance_tracker.finance_tracker.presentation.add_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedTextField
import com.finance_tracker.finance_tracker.core.ui.PrimaryButton
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.presentation.add_category.views.AddCategoryAppBar
import com.finance_tracker.finance_tracker.presentation.add_category.views.ChooseIconButton
import ru.alexgladkov.odyssey.compose.local.LocalRootController

private const val MinCategoryNameLength = 2

@Composable
fun AddCategoryScreen(transactionTypeTab: TransactionTypeTab) {
    StoredViewModel<AddCategoryViewModel> { viewModel ->
        val rootController = LocalRootController.current
        Column(modifier = Modifier.fillMaxSize()) {
            AddCategoryAppBar(textValue = if(transactionTypeTab == TransactionTypeTab.Expense) {
                "new_expense_category"
            } else {
                "new_income_category"
            })

            val chosenIcon by viewModel.chosenIcon.collectAsState()
            val newCategoryName by viewModel.categoryName.collectAsState()

            Row(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 24.dp
                    ),
            ) {

                val icons by viewModel.icons.collectAsState()
                val focusManager = LocalFocusManager.current
                ChooseIconButton(
                    chosenIcon = chosenIcon,
                    icons = icons,
                    onIconChoose = viewModel::onIconChoose,
                    onClick = { focusManager.clearFocus() }
                )

                CoinOutlinedTextField(
                    value = newCategoryName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        ),
                    onValueChange = {
                        viewModel.setCategoryName(it)
                    }
                )
            }

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
                text = stringResource("new_account_btn_add"),
                enable = newCategoryName.length >= MinCategoryNameLength,
                onClick = {
                    if (transactionTypeTab == TransactionTypeTab.Expense && newCategoryName != "") {
                        viewModel.addExpenseCategory(
                            categoryName = newCategoryName,
                            categoryIcon = chosenIcon
                        )
                        rootController.findRootController().popBackStack()
                    } else if (transactionTypeTab == TransactionTypeTab.Income && newCategoryName != "") {
                        viewModel.addIncomeCategory(
                            categoryName = newCategoryName,
                            categoryIcon = chosenIcon
                        )
                        rootController.findRootController().popBackStack()
                    }
                }
            )
        }
    }
}