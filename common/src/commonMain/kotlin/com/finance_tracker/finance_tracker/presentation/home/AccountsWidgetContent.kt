package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.ui.AccountCard
import com.finance_tracker.finance_tracker.domain.models.Account

private const val MaxAccountsForAddAccountWidget = 2

@Composable
fun AccountsWidgetContent(
    data: List<Account>,
    state: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        state = state,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(data) { account ->
            AccountCard(data = account)
        }
        if (data.size < MaxAccountsForAddAccountWidget) {
            item {
                AddAccountCard()
            }
        }
    }
}