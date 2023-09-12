package com.finance_tracker.finance_tracker.features.preset_currency.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.imePadding
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Currency
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun CurrenciesList(
    currencies: List<Currency>,
    selectedCurrency: Currency,
    modifier: Modifier = Modifier,
    onCurrencySelect: (Currency) -> Unit = {}
) {
    val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
    if (currencies.isNotEmpty()) {
        Box(
            modifier = modifier
        ) {
            LazyColumn(
                modifier = Modifier
                    .imePadding()
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 12.dp,
                    bottom = 12.dp + navigationBarsHeight
                )
            ) {
                items(currencies) { currency ->
                    CurrencyItem(
                        currency = currency,
                        onClick = { onCurrencySelect(currency) },
                        isCurrencySelected = currency == selectedCurrency
                    )
                }
            }

            TopBackgroundGradient(
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )

            BottomBackgroundGradient(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    } else {
        Text(
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp),
            text = stringResource(MR.strings.preset_currency_nothing_found),
            style = CoinTheme.typography.body1
        )
    }
}

@Composable
private fun BottomBackgroundGradient(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            CoinTheme.color.background,
                            CoinTheme.color.background
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CoinTheme.color.background)
                .imePadding()
                .navigationBarsPadding()
        )
    }
}

@Composable
private fun TopBackgroundGradient(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        CoinTheme.color.background,
                        Color.Transparent
                    )
                )
            )
    )
}