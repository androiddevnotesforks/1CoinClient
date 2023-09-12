package com.finance_tracker.finance_tracker.features.analytics

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.features.widgets.AnalyticsByCategoryWidget
import com.finance_tracker.finance_tracker.features.widgets.AnalyticsTrendWidget

val PieChartSize = 240.dp
val PieChartLabelSize = 20.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AnalyticsScreen() {
    ComposeScreen<AnalyticsViewModel>(withBottomNavigation = true) { viewModel ->

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            val selectedTransactionTypeTab by viewModel.transactionTypeTab.collectAsState()

            val pagerState = rememberPagerState()
            AnalyticsScreenAppBar(
                selectedTransactionTypeTab = selectedTransactionTypeTab,
                pagerState = pagerState,
                onTransactionTypeSelect = viewModel::onTransactionTypeSelect
            )
            val transactionTypeTabPage by viewModel.transactionTypeTabPage.collectAsState()
            LaunchedEffect(transactionTypeTabPage) {
                pagerState.animateScrollToPage(transactionTypeTabPage)
            }

            LaunchedEffect(pagerState.currentPage) {
                viewModel.onPageChanged(pagerState.currentPage)
            }

            HorizontalPager(
                pageCount = viewModel.transactionTypesCount,
                state = pagerState
            ) { page ->
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(top = 12.dp)
                ) {
                    val primaryCurrency by viewModel.primaryCurrency.collectAsState()
                    AnalyticsByCategoryWidget(
                        primaryCurrency = primaryCurrency,
                        monthTxsByCategoryDelegate = viewModel.getMonthTxsByCategoryDelegate(page),
                        selectedTransactionTypeTab = viewModel.getTransactionTypeTab(page)
                    )

                    AnalyticsTrendWidget(
                        trendsAnalyticsDelegate = viewModel.getTrendsAnalyticsDelegate(page),
                        transactionTypeTab = viewModel.getTransactionTypeTab(page)
                    )

                    Spacer(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(bottom = CoinPaddings.bottomNavigationBar)
                    )
                }
            }
        }
    }
}