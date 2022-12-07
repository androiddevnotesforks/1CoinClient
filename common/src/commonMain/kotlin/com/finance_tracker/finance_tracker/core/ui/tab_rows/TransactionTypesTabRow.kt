package com.finance_tracker.finance_tracker.core.ui.tab_rows

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.domain.models.TransactionType

enum class TransactionTypeTab(val textId: String) {
    Expense("tab_expense"),
    Income("tab_income"),
}

fun TransactionTypeTab.toTransactionType(): TransactionType {
    return when (this) {
        TransactionTypeTab.Expense -> TransactionType.Expense
        TransactionTypeTab.Income -> TransactionType.Income
    }
}

fun TransactionType.toTransactionTypeTab(): TransactionTypeTab {
    return when (this) {
        TransactionType.Expense -> TransactionTypeTab.Expense
        TransactionType.Income -> TransactionTypeTab.Income
    }
}

@Composable
fun TransactionTypesTabRow(
    modifier: Modifier = Modifier,
    selectedType: TransactionTypeTab = TransactionTypeTab.Expense,
    hasBottomDivider: Boolean = true,
    isHorizontallyCentered: Boolean = false,
    onSelect: (TransactionTypeTab) -> Unit = {}
) {
    val tabs = TransactionTypeTab.values()
    val tabToIndexMap = tabs
        .mapIndexed { index, tab -> tab to index }
        .toMap()

    CoinTabRow(
        data = tabs.map { stringResource(it.textId) },
        modifier = modifier,
        selectedTabIndex = tabToIndexMap.getValue(selectedType),
        hasBottomDivider = hasBottomDivider,
        isHorizontallyCentered = isHorizontallyCentered,
        onTabSelect = { index -> onSelect.invoke(tabs[index]) }
    )
}