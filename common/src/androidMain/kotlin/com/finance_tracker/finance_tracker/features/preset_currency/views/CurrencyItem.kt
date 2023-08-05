package com.finance_tracker.finance_tracker.features.preset_currency.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.domain.models.getDisplayName
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Currency

@Composable
internal fun CurrencyItem(
    currency: Currency,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isCurrencySelected: Boolean = false
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${currency.code} - ${currency.getDisplayName()}",
            style = CoinTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (isCurrencySelected) {
                CoinTheme.color.primary
            } else {
                CoinTheme.color.content
            }
        )
    }
}