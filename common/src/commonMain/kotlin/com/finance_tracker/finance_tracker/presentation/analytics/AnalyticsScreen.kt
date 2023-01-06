package com.finance_tracker.finance_tracker.presentation.analytics

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
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.ui.CoinWidget
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.presentation.analytics.models.TrendBarDetails
import com.finance_tracker.finance_tracker.presentation.analytics.peroid_bar_chart.CoinBarChartEntry
import com.finance_tracker.finance_tracker.presentation.analytics.peroid_bar_chart.PeriodBarChart
import com.finance_tracker.finance_tracker.presentation.analytics.txs_by_category_chart_block.TxsByCategoryChartBlock
import com.finance_tracker.finance_tracker.presentation.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.presentation.common.formatters.format

val PieChartSize = 240.dp
val PieChartLabelSize = 20.dp

@Composable
private fun List<TrendBarDetails>.mapToBarChartEntities(): List<CoinBarChartEntry<Float, Float>> {
    return mapIndexed { index, trendBarDetails ->
        CoinBarChartEntry(
            xValue = (index + 1).toFloat(),
            yMin = 0f,
            yMax = trendBarDetails.value.toFloat(),
            overviewTitle = trendBarDetails.title(),
            overviewValue = trendBarDetails.formattedValue()
        )
    }
}

@Composable
fun AnalyticsScreen() {
    StoredViewModel<AnalyticsViewModel> { viewModel ->

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            val selectedTransactionTypeTab by viewModel.transactionTypeTab.collectAsState()

            LaunchedEffect(selectedTransactionTypeTab) {
                viewModel.trendsAnalyticsDelegate.setSelectedTransactionType(
                    transactionType = selectedTransactionTypeTab.toTransactionType()
                )
                viewModel.monthTxsByCategoryDelegate.setSelectedTransactionType(
                    transactionType = selectedTransactionTypeTab.toTransactionType()
                )
            }

            AnalyticsScreenAppBar(
                selectedTransactionTypeTab = selectedTransactionTypeTab,
                onTransactionTypeSelect = viewModel::onTransactionTypeSelect
            )

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = 12.dp)
            ) {
                CoinWidget(
                    title = stringResource(selectedTransactionTypeTab.textId) + " " +
                            stringResource("analytics_by_category"),
                    withBorder = true
                ) {
                    val isLoading by viewModel.monthTxsByCategoryDelegate
                        .isLoadingMonthTxsByCategory.collectAsState()

                    val selectedMonth by viewModel.monthTxsByCategoryDelegate
                        .selectedMonth.collectAsState()

                    val monthTransactionsByCategory by viewModel.monthTxsByCategoryDelegate
                        .monthTransactionsByCategory.collectAsState()

                    TxsByCategoryChartBlock(
                        isLoading = isLoading,
                        selectedMonth = selectedMonth,
                        monthTransactionsByCategory = monthTransactionsByCategory,
                        onMonthSelect = viewModel::onMonthSelect
                    )
                }

                CoinWidget(
                    title = stringResource(selectedTransactionTypeTab.textId) + " " +
                            stringResource("analytics_trend"),
                    withBorder = true
                ) {
                    val selectedPeriodChip by viewModel.trendsAnalyticsDelegate.selectedPeriodChipFlow.collectAsState()
                    val trend by viewModel.trendsAnalyticsDelegate.trendFlow.collectAsState()
                    val total by viewModel.trendsAnalyticsDelegate.totalFlow.collectAsState()

                    PeriodBarChart(
                        barChartEntries = trend.mapToBarChartEntities(),
                        defaultTitle = stringResource("total") + " " +
                                stringResource(selectedTransactionTypeTab.textId),
                        defaultValue = total.format(mode = AmountFormatMode.NoSigns),
                        selectedPeriodChip = selectedPeriodChip,
                        onChipSelect = { viewModel.trendsAnalyticsDelegate.setPeriodChip(it) }
                    )
                }

                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = CoinPaddings.bottomNavigationBar)
                )
            }
        }
    }
}