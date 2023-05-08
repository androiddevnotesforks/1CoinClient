package com.finance_tracker.finance_tracker.features.plans

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.features.plans.views.BudgetByCategoriesWidget
import com.finance_tracker.finance_tracker.features.plans.views.PlansTopBar

@Composable
fun PlansScreen() {
    ComposeScreen<PlansScreenViewModel> { viewModel ->

        Column {
            PlansTopBar()

            BudgetByCategoriesWidget()
        }
    }
}