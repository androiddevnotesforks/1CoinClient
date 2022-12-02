package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinWidget
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypesTabRow

@Composable
fun AnalyticsScreen() {
    StoredViewModel<AnalyticsViewModel> { viewModel ->
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

            CoinWidget(
                title = stringResource(selectedTransactionTypeTab.textId) + " " +
                        stringResource("analytics_by_category")
            ) {
                val selectedMonth by viewModel.selectedMonth.collectAsState()
                val monthTransactionsByCategory by viewModel.monthTransactionsByCategory.collectAsState()
                CategoriesPieChart(
                    selectedMonth = selectedMonth,
                    monthTransactionsByCategory = monthTransactionsByCategory,
                    onMonthSelect = viewModel::onMonthSelect
                )
            }
        }
    }
}