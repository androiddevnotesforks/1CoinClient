package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.formatters.isCurrencyPositionAtStart
import com.finance_tracker.finance_tracker.domain.models.Currency

@ExperimentalMaterialApi
@Composable
fun AmountTextField(
    currency: Currency?,
    amount: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var amountFontSize by remember { mutableStateOf(style.fontSize) }
        val currencySymbol = currency?.symbol
        if (currencySymbol != null && isCurrencyPositionAtStart()) {
            CurrencyText(
                modifier = Modifier.padding(end = 4.dp),
                currencySymbol = currencySymbol,
                fontSize = amountFontSize,
                color = style.color
            )
        }
        CompositionLocalProvider(
            LocalTextInputService provides null
        ) {
            AutoSizeTextField(
                value = amount,
                style = style,
                readOnly = true,
                onSizeChange = { amountFontSize = it },
                onClick = onClick
            )
        }
        if (currencySymbol != null && !isCurrencyPositionAtStart()) {
            CurrencyText(
                modifier = Modifier.padding(start = 4.dp),
                currencySymbol = currencySymbol,
                fontSize = amountFontSize,
                color = style.color
            )
        }
    }
}

@Composable
private fun CurrencyText(
    currencySymbol: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        modifier = modifier,
        text = currencySymbol,
        fontSize = fontSize,
        color = color,
        style = style
    )
}