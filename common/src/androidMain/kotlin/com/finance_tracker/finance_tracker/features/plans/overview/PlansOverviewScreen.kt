package com.finance_tracker.finance_tracker.features.plans.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.features.plans.overview.views.PlansOverviewTopBar
import com.finance_tracker.finance_tracker.features.plans.overview.views.category_expense_limits.ExpenseLimitsWidget
import com.finance_tracker.finance_tracker.features.plans.overview.views.month_expense_limit.MonthExpenseLimitWidget
import com.finance_tracker.finance_tracker.features.plans.overview.views.plans_overview.PlansOverviewState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlansOverviewScreen() {
    ComposeScreen<PlansOverviewViewModel>(withBottomNavigation = true) { viewModel ->

        val pagerState = rememberPagerState(PlansOverviewState.InitialMonthIndex)
        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage, pagerState)
        }

        Column(modifier = Modifier.fillMaxSize()) {
            val plansOverviewState by viewModel.plansOverviewState.collectAsState()
            PlansOverviewTopBar(
                planPeriodState = plansOverviewState.planPeriodState,
                planPeriodCallback = viewModel
            )

            LaunchedEffect(pagerState.currentPage) {
                viewModel.onPageChange(pagerState.currentPage)
            }

            HorizontalPager(
                state = pagerState,
                pageCount = PlansOverviewState.MaxMonths
            ) { page ->
                val flow = remember(page) { viewModel.getState(page) }
                val state by flow.collectAsState()
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    MonthExpenseLimitWidget(
                        monthExpenseLimitChartData = state.monthExpenseLimitChartData,
                        onSetLimitClick = viewModel::onSetLimitClick
                    )

                    ExpenseLimitsWidget(
                        onAddLimitClick = viewModel::onAddCategoryExpenseLimitClick,
                        plans = state.plans,
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
}