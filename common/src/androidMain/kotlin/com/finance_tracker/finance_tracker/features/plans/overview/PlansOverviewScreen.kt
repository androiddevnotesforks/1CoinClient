package com.finance_tracker.finance_tracker.features.plans.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.features.plans.overview.views.PlansOverviewTopBar
import com.finance_tracker.finance_tracker.features.plans.overview.views.category_expense_limits.ExpenseLimitsWidget
import com.finance_tracker.finance_tracker.features.plans.overview.views.month_expense_limit.MonthExpenseLimitWidget


@Composable
fun PlansOverviewScreen() {
    ComposeScreen<PlansOverviewViewModel>(withBottomNavigation = true) { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage)
        }

        Column(modifier = Modifier.fillMaxSize()) {
            PlansOverviewTopBar()

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                val monthExpenseLimitChartData by viewModel.monthExpenseLimitChartData.collectAsState()
                MonthExpenseLimitWidget(
                    monthExpenseLimitChartData = monthExpenseLimitChartData
                )

                val plans by viewModel.plans.collectAsState()
                ExpenseLimitsWidget(
                    onAddLimitClick = viewModel::onAddCategoryExpenseLimitClick,
                    plans = plans,
                    onLimitClick = viewModel::onCategoryExpenseLimitClick
                )

                Spacer(
                    modifier = Modifier
                        .padding(bottom = CoinPaddings.bottomNavigationBar)
                        .navigationBarsPadding()
                )
            }
        }
    }
}