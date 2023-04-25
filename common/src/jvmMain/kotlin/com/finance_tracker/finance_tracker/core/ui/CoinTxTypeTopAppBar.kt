package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypesTabRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CoinTxTypeTopAppBar(
    title: @Composable () -> Unit,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    selectedTransactionTypeTab: TransactionTypeTab = TransactionTypeTab.Expense,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    appBarHeight: Dp = AppBarHeight,
    onTransactionTypeSelect: (TransactionTypeTab) -> Unit = {}
) {
    Column(modifier = modifier) {
        CoinTopAppBar(
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            appBarHeight = appBarHeight
        )
        TransactionTypesTabRow(
            selectedType = selectedTransactionTypeTab,
            pagerState = pagerState,
            onSelect = onTransactionTypeSelect
        )
    }
}