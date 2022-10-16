package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.*
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
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.theme.CoinTheme

@Composable
fun AccountCard(
    data: Account,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .size(
                width = 160.dp,
                height = 128.dp
            ),
        backgroundColor = data.color,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column {
            Icon(
                painter = painterResource(R.drawable.ic_wallet_active),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
                    .size(29.dp),
                tint = CoinTheme.color.primaryVariant
            )
            Text(
                text = data.balance.toString(),
                style = CoinTheme.typography.h5,
                color = CoinTheme.color.primaryVariant,
                modifier = Modifier
                    .padding(top = 30.dp,
                    start = 16.dp)
            )
            Text(
                text = data.name,
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.primaryVariant.copy(alpha = 0.5f),
                modifier = Modifier
                    .padding(start = 16.dp))
        }
    }
}

@Preview
@Composable
fun AccountCardPreview() {

    //TODO - ПРЕВЬЮ ДАННЫЕ
    val testCard = Account(
        id = 0,
        type = Account.Type.DebitCard,
        name = "Debit card (*5841)",
        balance = 2200.5,
        color = Color(0xFFD50000),
    )
    
    AccountCard(data = testCard)
}