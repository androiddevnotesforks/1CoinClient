package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.systemBarsPadding
import com.finance_tracker.finance_tracker.core.navigation.tabs.TabsNavigationTree
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun HomeScreen() {
    StoredViewModel<HomeViewModel> { viewModel ->
        val rootController = LocalRootController.current
        val navController = rootController.parentRootController as MultiStackRootController
        val accounts by viewModel.accounts.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        val accountsLazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            viewModel.events
                .filterNotNull()
                .onEach { event ->
                    handleEvent(
                        event,
                        coroutineScope,
                        accountsLazyListState
                    )
                }
                .launchIn(this)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CoinTheme.color.background)
                .systemBarsPadding()
        ) {


            val totalBalance by viewModel.totalBalance.collectAsState()
            HomeTopBar(totalBalance = totalBalance)

            WidgetHeader(
                text = "home_my_accounts",
                onClick = {
                    navController.switchTab(TabsNavigationTree.Accounts.ordinal)
                }
            ) {
                AccountsWidget(
                    data = accounts,
                    state = accountsLazyListState
                )
            }

            WidgetHeader(
                text = "home_last_transactions",
                onClick = {
                    navController.switchTab(TabsNavigationTree.Transactions.ordinal)
                }
            ) {
                val lastTransactions by viewModel.lastTransactions.collectAsState()
                LastTransactionsWidget(lastTransactions)
            }
        }
    }
}