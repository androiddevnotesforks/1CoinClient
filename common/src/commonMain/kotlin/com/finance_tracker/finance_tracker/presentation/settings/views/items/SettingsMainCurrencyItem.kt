package com.finance_tracker.finance_tracker.presentation.settings.views.items

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.add_account.views.CurrencySelector
import com.finance_tracker.finance_tracker.presentation.settings.views.ListItem
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsMainCurrencyItem(
    selectedCurrency: Currency,
    modifier: Modifier = Modifier,
    onCurrencySelect: (Currency) -> Unit = {},
    onCurrencyClick: (Currency) -> Unit = {}
) {
    ListItem(
        modifier = modifier,
        iconLeftPainter = rememberVectorPainter("ic_currency"),
        text = stringResource(MR.strings.settings_main_currency),
        iconRight = {
            CurrencySelector(
                modifier = Modifier.padding(end = 8.dp),
                items = Currency.list,
                selectedCurrency = selectedCurrency,
                onCurrencySelect = onCurrencySelect,
                onCurrencyClick = onCurrencyClick,
                xOffset = 248.dp,
                yOffset = 12.dp
            )
        },
    )
}