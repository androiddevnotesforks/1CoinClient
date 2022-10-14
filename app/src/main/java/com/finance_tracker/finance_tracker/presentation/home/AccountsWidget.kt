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
fun accountsWidget() {

    //TODO - ВРЕМЕННЫЕ ФЕЙКОВЫЕ ДАННЫЕ
    val accounts = listOf(
        Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFD50000),
        ),
        Account(
            id = 1,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFE67C73),
        ),
        Account(
            id = 2,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFF4511E),
        ),
        Account(
            id = 3,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFF6BF26),
        ),
        Account(
            id = 4,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF33B679),
        ),  Account(
            id = 5,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF0B8043),
        ),
    )

    LazyRow(
        modifier = Modifier
            .padding(top = 12.dp),
        contentPadding = PaddingValues(start = 4.dp)
    ) {
        items(accounts) { account ->
            AccountCard(data = account)
        }
    }
}

@Preview
@Composable
fun accountsWidgetPreview() {
    accountsWidget()
}