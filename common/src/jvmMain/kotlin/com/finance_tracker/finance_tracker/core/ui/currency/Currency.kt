package com.finance_tracker.finance_tracker.core.ui.currency

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Currency

@Composable
fun CurrencyView(
    currency: Currency,
    modifier: Modifier = Modifier,
    tint: Color = CoinTheme.color.content,
    textStyle: TextStyle = CoinTheme.typography.body1
) {
    Text(
        modifier = modifier,
        text = "${currency.code}(${currency.symbol})",
        color = tint,
        style = textStyle
    )
}