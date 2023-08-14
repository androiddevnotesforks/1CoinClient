package com.finance_tracker.finance_tracker.features.accounts

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.theme.provideThemeImage
import com.finance_tracker.finance_tracker.core.ui.AccountCard
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.core.ui.EmptyStub
import com.finance_tracker.finance_tracker.core.ui.drag_and_drop.grid.ReorderableItem
import com.finance_tracker.finance_tracker.core.ui.drag_and_drop.grid.detectReorderAfterLongPress
import com.finance_tracker.finance_tracker.core.ui.drag_and_drop.grid.rememberReorderableLazyGridState
import com.finance_tracker.finance_tracker.core.ui.drag_and_drop.grid.reorderable
import com.finance_tracker.finance_tracker.domain.models.Account
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun AccountsScreen() {
    ComposeScreen<AccountsViewModel> { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action,
                baseLocalsStorage
            )
        }

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        Column {
            AccountsAppBar(
                onAddAccountClick = viewModel::onAddAccountClick,
                onBackClick = viewModel::onBackClick
            )

            val accounts by viewModel.accounts.collectAsState()

            if (accounts.isEmpty()) {
                EmptyStub(
                    image = painterResource(
                        provideThemeImage(
                            darkFile = MR.images.accounts_empty_dark,
                            lightFile = MR.images.accounts_empty_light
                        )
                    ),
                    text = stringResource(MR.strings.add_account),
                    onClick = viewModel::onAddAccountClick
                )
            } else {
                AccountsList(
                    accounts = accounts,
                    onAccountClick = viewModel::onAccountClick,
                    onCardMove = viewModel::onCardMove
                )
            }
        }
    }
}

@Composable
private fun AccountsList(
    accounts: List<Account>,
    onAccountClick: (Account) -> Unit,
    onCardMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
    val reorderableState = rememberReorderableLazyGridState(
        onMove = { from, to ->
            onCardMove(from.index, to.index)
        }
    )

    LazyVerticalGrid(
        state = reorderableState.gridState,
        columns = GridCells.Fixed(count = 2),
        modifier = modifier
            .fillMaxSize()
            .reorderable(reorderableState),
        contentPadding = PaddingValues(
            bottom = CoinPaddings.bottomNavigationBar + navigationBarsHeight,
            start = 16.dp,
            end = 16.dp,
            top = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = accounts, key = { it.id }) { account ->
            ReorderableItem(reorderableState = reorderableState, key = account.id) { isDragging: Boolean ->
                val elevation by animateDpAsState(if (isDragging) 12.dp else 0.dp)

                AccountCard(
                    data = account,
                    onClick = { onAccountClick(account) },
                    elevation = elevation,
                    modifier = Modifier.detectReorderAfterLongPress(reorderableState)
                )
            }
        }
    }
}
