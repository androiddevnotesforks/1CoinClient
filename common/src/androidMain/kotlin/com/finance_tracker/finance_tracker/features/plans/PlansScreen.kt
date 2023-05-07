package com.finance_tracker.finance_tracker.features.plans

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.domain.models.Budget
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.features.plans.views.BudgetByCategoriesWidget
import com.finance_tracker.finance_tracker.features.plans.views.PlansTopBar

@Composable
fun PlansScreen() {
    ComposeScreen<PlansScreenViewModel> { viewModel ->

        Column {
            PlansTopBar()

            val budgets = listOf(
                Budget(
                    spentAmount = 60.0,
                    limitAmount = 150.0,
                    category = Category(
                        id = 0,
                        name = "Health",
                        icon = MR.files.ic_category_9
                    )
                ),
                Budget(
                    spentAmount = 60.0,
                    limitAmount = 150.0,
                    category = Category(
                        id = 0,
                        name = "Health",
                        icon = MR.files.ic_category_9
                    )
                ),
                Budget(
                    spentAmount = 60.0,
                    limitAmount = 150.0,
                    category = Category(
                        id = 0,
                        name = "Health",
                        icon = MR.files.ic_category_9
                    )
                ),Budget(
                    spentAmount = 60.0,
                    limitAmount = 150.0,
                    category = Category(
                        id = 0,
                        name = "Health",
                        icon = MR.files.ic_category_9
                    )
                ),
                Budget(
                    spentAmount = 60.0,
                    limitAmount = 150.0,
                    category = Category(
                        id = 0,
                        name = "Health",
                        icon = MR.files.ic_category_9
                    )
                ),
                Budget(
                    spentAmount = 60.0,
                    limitAmount = 150.0,
                    category = Category(
                        id = 0,
                        name = "Health",
                        icon = MR.files.ic_category_9
                    )
                ),
                Budget(
                    spentAmount = 60.0,
                    limitAmount = 150.0,
                    category = Category(
                        id = 0,
                        name = "Health",
                        icon = MR.files.ic_category_9
                    )
                ),
            )

            BudgetByCategoriesWidget(budgets = budgets)
        }
    }
}