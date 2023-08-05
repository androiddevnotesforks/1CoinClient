package com.finance_tracker.finance_tracker.features.preset_currency

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.filterByQuery
import com.finance_tracker.finance_tracker.core.common.getLocaleLanguage
import com.finance_tracker.finance_tracker.core.common.imePadding
import com.finance_tracker.finance_tracker.core.common.keyboardExpandedAsState
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.core.ui.button.PrimaryButton
import com.finance_tracker.finance_tracker.features.preset_currency.views.CurrenciesList
import com.finance_tracker.finance_tracker.features.preset_currency.views.PresetCurrencyAppBar
import com.finance_tracker.finance_tracker.features.preset_currency.views.SearchTextField
import com.finance_tracker.finance_tracker.features.preset_currency.views.SelectedCurrency
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun PresetCurrencyScreen() {
    ComposeScreen<PresetCurrencyViewModel> { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage
            )
        }

        val state by viewModel.state.collectAsState()

        Column {
            PresetCurrencyAppBar(
                onBackClick = viewModel::onBackClick
            )

            val isKeyboardExpanded by keyboardExpandedAsState()
            val selectedPrimaryCurrency = state.selectedCurrency
            AnimatedVisibility(!isKeyboardExpanded) {
                Column {
                    Spacer(Modifier.height(16.dp))
                    SelectedCurrency(currency = selectedPrimaryCurrency)
                }
            }

            val focusManager = LocalFocusManager.current
            LaunchedEffect(isKeyboardExpanded) {
                if (!isKeyboardExpanded) {
                    focusManager.clearFocus()
                }
            }
            SearchTextField(
                searchQuery = state.searchQuery,
                onValueChange = viewModel::onSearchQueryChange
            )

            Text(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp),
                text = stringResource(MR.strings.preset_currency_other_currency),
                style = CoinTheme.typography.body1_medium
            )

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                val localeLanguage = getLocaleLanguage()
                val currencies = state.currencies
                val searchQuery = state.searchQuery
                val filteredCurrencies by remember(currencies, searchQuery, localeLanguage) {
                    derivedStateOf { currencies.filterByQuery(searchQuery, localeLanguage) }
                }

                CurrenciesList(
                    currencies = filteredCurrencies,
                    selectedCurrency = selectedPrimaryCurrency,
                    onCurrencySelect = viewModel::onCurrencySelect
                )

                val context = LocalContext.current
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        )
                        .imePadding()
                        .navigationBarsPadding(),
                    text = stringResource(MR.strings.preset_currency_continue),
                    onClick = { viewModel.onContinueClick(context) }
                )
            }
        }
    }
}