package com.finance_tracker.finance_tracker.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.add_account.views.CurrencySelector
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun SettingsSheet(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    StoredViewModel<SettingsSheetViewModel> { viewModel ->

        val rootController = LocalRootController.current

        val chosenCurrency by viewModel.chosenCurrency.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        bottom = 16.dp
                    )
                    .align(Alignment.CenterHorizontally),
                text = stringResource("settings_top_text"),
                style = CoinTheme.typography.h4
            )
            Divider(
                color = CoinTheme.color.dividers,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            Row(
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        bottom = 20.dp
                    )
                    .height(24.dp)
            ) {
                Icon(
                    painter = rememberVectorPainter(id = "ic_category_1"),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 8.dp
                        )
                        .size(20.dp)
                        .align(Alignment.CenterVertically),
                    tint = CoinTheme.color.content
                )
                Text(
                    text = stringResource("settings_main_currency"),
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    style = CoinTheme.typography.body1
                )
                Spacer(Modifier.weight(1f))
                CurrencySelector(
                    items = Currency.list,
                    selectedCurrency = chosenCurrency,
                    onCurrencySelect = viewModel::onCurrencySelect,
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .height(24.dp)
                    .clickable {
                        rootController.findRootController().push(MainNavigationTree.CategorySettings.name)
                        onCloseClick()
                    }
            ) {
                Icon(
                    painter = rememberVectorPainter(id = "ic_category_1"),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 8.dp
                        )
                        .size(20.dp)
                        .align(Alignment.CenterVertically),
                    tint = CoinTheme.color.content
                )
                Text(
                    text = stringResource("settings_categories"),
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    style = CoinTheme.typography.body1
                )
                Spacer(Modifier.weight(1f))
            }
        }
    }
}