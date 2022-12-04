package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinWidget
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypesTabRow
import com.finance_tracker.finance_tracker.presentation.analytics.txs_by_category_chart_block.TxsByCategoryChartBlock

val PieChartSize = 240.dp
val PieChartLabelSize = 20.dp

@Composable
fun AnalyticsScreen() {
    StoredViewModel<AnalyticsViewModel> { viewModel ->

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CoinTheme.color.background)
                .statusBarsPadding()
        ) {
            AnalyticsScreenAppBar()
            val selectedTransactionTypeTab by viewModel.transactionTypeTab.collectAsState()
            TransactionTypesTabRow(
                selectedType = selectedTransactionTypeTab,
                onSelect = viewModel::onTransactionTypeSelect
            )

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                CoinWidget(
                    title = stringResource(selectedTransactionTypeTab.textId) + " " +
                            stringResource("analytics_by_category")
                ) {
                    val isLoading by viewModel.isLoadingMonthTxsByCategory.collectAsState()
                    val selectedMonth by viewModel.selectedMonth.collectAsState()
                    val monthTransactionsByCategory by viewModel.monthTransactionsByCategory.collectAsState()
                    TxsByCategoryChartBlock(
                        isLoading = isLoading,
                        selectedMonth = selectedMonth,
                        monthTransactionsByCategory = monthTransactionsByCategory,
                        onMonthSelect = viewModel::onMonthSelect
                    )
                }
                Spacer(
                    modifier = Modifier
                        .padding(bottom = CoinPaddings.bottomNavigationBar)
                )
            }
        }
    }
}