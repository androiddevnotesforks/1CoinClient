package com.finance_tracker.finance_tracker.presentation.detail_account

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.UpdateSystemBarsConfigEffect
import com.finance_tracker.finance_tracker.core.common.pagination.AutoRefreshList
import com.finance_tracker.finance_tracker.core.common.pagination.collectAsLazyPagingItems
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffold
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffoldScopeInstance.align
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.ScrollStrategy
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.rememberCollapsingToolbarScaffoldState
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.presentation.detail_account.views.AccountNameText
import com.finance_tracker.finance_tracker.presentation.detail_account.views.DetailAccountAppBar
import com.finance_tracker.finance_tracker.presentation.detail_account.views.DetailAccountExpandedAppBar
import com.finance_tracker.finance_tracker.presentation.detail_account.views.EditButton
import org.koin.core.parameter.parametersOf
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
@Suppress("MagicNumber")
fun DetailAccountScreen(
    account: Account
) {
    StoredViewModel<DetailAccountViewModel>(
        parameters = { parametersOf(account) }
    ) { viewModel ->
        UpdateSystemBarsConfigEffect {
            isStatusBarLight = false
        }

        val accountData by viewModel.accountData.collectAsState()
        val navController = LocalRootController.current.findRootController()
        val state = rememberCollapsingToolbarScaffoldState()
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = state,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                DetailAccountAppBar(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = 1f - state.toolbarState.progress
                        },
                    color = accountData.colorModel.color
                )
                DetailAccountExpandedAppBar(
                    modifier = Modifier
                        .parallax(0.4f),
                    contentAlpha = state.toolbarState.progress,
                    color = accountData.colorModel.color,
                    amount = accountData.balance,
                    iconId = accountData.iconId
                )

                AppBarIcon(
                    modifier = Modifier
                        .padding(
                            horizontal = 4.dp,
                            vertical = 6.dp
                        )
                        .statusBarsPadding()
                        .align(Alignment.TopStart),
                    painter = rememberVectorPainter("ic_arrow_back"),
                    onClick = { navController.popBackStack() },
                    tint = CoinTheme.color.primaryVariant
                )

                AccountNameText(
                    name = accountData.name,
                    state = state
                )

                EditButton(
                    state = state,
                    tint = accountData.colorModel.color,
                    onClick = {
                        navController.push(
                            screen = MainNavigationTree.AddAccount.name,
                            params = account
                        )
                    }
                )
            }
        ) {
            val transactions = viewModel.paginatedTransactions.collectAsLazyPagingItems()

            AutoRefreshList(transactions)

            val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
            CommonTransactionsList(
                transactions = transactions,
                contentPadding = PaddingValues(
                    bottom = navigationBarsHeight
                ),
                onClick = {
                    navController.push(
                        screen = MainNavigationTree.AddTransaction.name,
                        params = it.transaction
                    )
                }
            )
        }
    }
}