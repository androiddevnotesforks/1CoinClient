package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.presentation.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.presentation.common.formatters.format

val AccountCardHeight = 128.dp
val AccountCardWidth = 160.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountCard(
    data: Account,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(
                width = AccountCardWidth,
                height = AccountCardHeight
            ),
        backgroundColor = data.colorModel.color,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        elevation = 0.dp
    ) {
        Column {
            Icon(
                painter = rememberVectorPainter(data.iconId),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
                    .size(29.dp),
                tint = CoinTheme.color.primaryVariant
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = data.balance.format(mode = AmountFormatMode.NegativeSign),
                style = CoinTheme.typography.h5,
                color = CoinTheme.color.primaryVariant,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            val accountName = if (data.type == Account.Type.Cash) {
                "${data.name} (${data.balance.currency.symbol})"
            } else {
                data.name
            }
            Text(
                text = accountName,
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.primaryVariant.copy(alpha = 0.5f),
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        bottom = 16.dp
                    )
            )
        }
    }
}