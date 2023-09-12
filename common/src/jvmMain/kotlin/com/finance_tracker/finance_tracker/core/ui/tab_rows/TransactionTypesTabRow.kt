package com.finance_tracker.finance_tracker.core.ui.tab_rows

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TransactionTypesTabRow(
    modifier: Modifier = Modifier,
    transactionTypesMode: TransactionTypesMode = TransactionTypesMode.Main,
    selectedType: TransactionTypeTab = TransactionTypeTab.Expense,
    hasBottomDivider: Boolean = true,
    isHorizontallyCentered: Boolean = false,
    pagerState: PagerState? = null,
    onSelect: (TransactionTypeTab) -> Unit = {}
) {
    val tabs = transactionTypesMode.types
    val tabToIndexMap = tabs
        .mapIndexed { index, tab -> tab to index }
        .toMap()

    CoinTabRow(
        data = tabs.map { stringResource(it.textId) }.toImmutableList(),
        modifier = modifier,
        selectedTabIndex = tabToIndexMap.getValue(selectedType),
        hasBottomDivider = hasBottomDivider,
        isHorizontallyCentered = isHorizontallyCentered,
        pagerState = pagerState,
        onTabSelect = { index -> onSelect(tabs[index]) }
    )
}