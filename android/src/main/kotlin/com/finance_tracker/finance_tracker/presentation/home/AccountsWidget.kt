package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.ui.AccountCard
import com.finance_tracker.finance_tracker.domain.models.Account

@Composable
fun AccountsWidget(
    modifier: Modifier = Modifier,
    data: List<Account>
) {

    LazyRow(
        modifier = modifier
            .padding(top = 12.dp),
        contentPadding = PaddingValues(start = 4.dp)
    ) {
        items(data) { account ->
            AccountCard(data = account)
        }
    }
}

@Preview
@Composable
fun AccountsWidgetPreview() {

    //TODO - ПРЕВЬЮ ДАННЫЕ
    val testCard = listOf(
        Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            balance = 2200.5,
            color = Color(0xFFD50000),
        )
    )

    AccountsWidget(data = testCard)
}