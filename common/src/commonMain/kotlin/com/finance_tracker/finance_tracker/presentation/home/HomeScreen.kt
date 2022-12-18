package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import com.finance_tracker.finance_tracker.core.common.systemBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.navigation.tabs.TabsNavigationTree
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
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
        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage, accountsLazyListState)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CoinTheme.color.background)
                .systemBarsPadding()
        ) {
            val totalBalance by viewModel.totalBalance.collectAsState()
            HomeTopBar(totalBalance = totalBalance)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Widget(
                    modifier = Modifier.padding(top = 12.dp),
                    text = "home_my_accounts",
                    onClick = {
                        navController.switchTab(TabsNavigationTree.Accounts.ordinal)
                    }
                ) {
                    AccountsWidgetContent(
                        data = accounts,
                        state = accountsLazyListState
                    )
                }

                Widget(
                    text = "home_last_transactions",
                    onClick = {
                        navController.switchTab(TabsNavigationTree.Transactions.ordinal)
                    }
                ) {
                    val lastTransactions by viewModel.lastTransactions.collectAsState()
                    LastTransactionsWidgetContent(lastTransactions)
                }
            }
        }
    }
}