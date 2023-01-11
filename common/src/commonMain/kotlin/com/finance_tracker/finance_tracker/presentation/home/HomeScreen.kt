package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinWidget

@Composable
fun HomeScreen() {
    StoredViewModel<HomeViewModel> { viewModel ->
        val accounts by viewModel.accounts.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        val accountsLazyListState = rememberLazyListState()
        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage, accountsLazyListState)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CoinTheme.color.background)
        ) {
            val totalBalance by viewModel.totalBalance.collectAsState()
            HomeTopBar(
                totalBalance = totalBalance,
                onSettingsClick = viewModel::onSettingsClick
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 12.dp)
            ) {
                CoinWidget(
                    title = stringResource("home_my_accounts"),
                    withHorizontalPadding = false,
                    onClick = viewModel::onMyAccountsClick
                ) {
                    AccountsWidgetContent(
                        data = accounts,
                        state = accountsLazyListState,
                        onAccountClick = viewModel::onAccountClick,
                        onAddAccountClick = viewModel::onAddAccountClick
                    )
                }

                CoinWidget(
                    title = stringResource("home_last_transactions"),
                    withBorder = true,
                    onClick = viewModel::onLastTransactionsClick
                ) {
                    val lastTransactions by viewModel.lastTransactions.collectAsState()
                    LastTransactionsWidgetContent(
                        lastTransactions = lastTransactions,
                        onTransactionClick = viewModel::onTransactionClick
                    )
                }

                Spacer(
                    modifier = Modifier
                        .padding(bottom = CoinPaddings.bottomNavigationBar)
                        .navigationBarsPadding()
                )
            }
        }
    }
}