package com.finance_tracker.finance_tracker.presentation.accounts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.core.navigation.TabNavGraph
import com.finance_tracker.finance_tracker.domain.models.Account
import com.ramcosta.composedestinations.annotation.Destination


@TabNavGraph
@Destination
@Composable
fun AccountsScreen() {

    val cards = listOf(
        Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFD50000),
        ),
        Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFE67C73),
        ),
        Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFF4511E),
        ),
        Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFFF6BF26),
        ),
        Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF33B679),
        ),  Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF0B8043),
        ),
        Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF039BE5),
        ),
        Account(
            id = 0,
            type = Account.Type.DebitCard,
            name = "Debit card (*5841)",
            color = Color(0xFF3F51B5),
        ),
    )

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(cards) { card ->
            AccountCard(data = card)
        }
    }
}

@Composable
fun AccountCard(
    data: Account,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .height(128.dp)
            .width(160.dp),
        backgroundColor = data.color,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column {
            Icon(painter = painterResource(R.drawable.ic_wallet_active),
                contentDescription = "Credit card icon",
                modifier = modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .height(29.dp)
                    .width(29.dp),
                tint = Color.White
            )
            Text(text = data.name,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.5f),
                modifier = modifier
                    .padding(start = 16.dp))
        }
    }
}

@Preview
@Composable
fun AccountsScreenPreview() {
    AccountsScreen()
}
