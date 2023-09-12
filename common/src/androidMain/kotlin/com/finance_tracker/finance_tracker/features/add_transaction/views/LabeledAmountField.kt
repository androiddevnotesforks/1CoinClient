package com.finance_tracker.finance_tracker.features.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AmountTextField
import com.finance_tracker.finance_tracker.domain.models.Currency
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun LabeledAmountTextField(
    currency: Currency?,
    amount: String,
    active: Boolean,
    label: String,
    amountFontSize: TextUnit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(CoinTheme.color.background)
            .padding(horizontal = 16.dp)
            .scaleClickAnimation()
            .noRippleClickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AmountTextField(
            currency = currency,
            amount = amount,
            style = CoinTheme.typography.h1.copy(
                color = if (active) {
                    CoinTheme.color.content
                } else {
                    CoinTheme.color.secondary
                },
                fontSize = amountFontSize
            ),
            onClick = onClick
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = label,
            color = CoinTheme.color.secondary,
            style = CoinTheme.typography.subtitle2
        )
    }
}