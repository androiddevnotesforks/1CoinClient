package com.finance_tracker.finance_tracker.core.ui.tab_rows

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

enum class TransactionTypesMode(val types: ImmutableList<TransactionTypeTab>)  {
    Main(
        types = persistentListOf(TransactionTypeTab.Expense, TransactionTypeTab.Income)
    ),
    Full(
        types = TransactionTypeTab.values().toList().toImmutableList()
    )
}