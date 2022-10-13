package com.finance_tracker.finance_tracker.presentation.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.finance_tracker.finance_tracker.domain.models.Account

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
fun AccountCardPreview() {
    
    val testCard = Account(
        id = 0,
        type = Account.Type.DebitCard,
        name = "Debit card (*5841)",
        color = Color(0xFFD50000),
    )
    
    AccountCard(data = testCard)
}