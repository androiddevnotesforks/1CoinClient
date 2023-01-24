package com.finance_tracker.finance_tracker.presentation.settings.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.add_account.views.CurrencySelector
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsMainCurrencyItem(
    selectedCurrency: Currency,
    modifier: Modifier = Modifier,
    onCurrencySelect: (Currency) -> Unit = {},
    onCurrencyClick: (Currency) -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp
            )
            .fillMaxWidth()
    ) {
        Icon(
            painter = rememberVectorPainter(id = "ic_currency"),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    end = 8.dp
                )
                .size(24.dp)
                .align(Alignment.CenterVertically),
            tint = CoinTheme.color.content
        )
        Text(
            text = stringResource(MR.strings.settings_main_currency),
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically),
            style = CoinTheme.typography.body1_medium
        )
        Spacer(Modifier.weight(1f))
        CurrencySelector(
            items = Currency.list,
            selectedCurrency = selectedCurrency,
            onCurrencySelect = onCurrencySelect,
            onCurrencyClick = onCurrencyClick,
            xOffset = 248.dp,
            yOffset = 12.dp
        )
    }
}