package com.finance_tracker.finance_tracker.presentation.accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.ui.AccountCard

@Composable
fun AccountsScreen() {
    StoredViewModel<AccountsViewModel> { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage)
        }

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        Column {
            AccountsAppBar(
                onAddAccountClick = viewModel::onAddAccountClick
            )

            val accounts by viewModel.accounts.collectAsState()
            val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                modifier = Modifier
                    .fillMaxHeight(),
                contentPadding = PaddingValues(
                    bottom = CoinPaddings.bottomNavigationBar + navigationBarsHeight,
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(accounts) { account ->
                    AccountCard(
                        data = account,
                        onClick = { viewModel.onAccountClick(account) }
                    )
                }
            }
        }
    }
}
