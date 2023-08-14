package com.finance_tracker.finance_tracker.features.detail_account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.UpdateSystemBarsConfigEffect
import com.finance_tracker.finance_tracker.core.common.pagination.collectAsLazyPagingItems
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.toDp
import com.finance_tracker.finance_tracker.core.common.toUIColor
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffold
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffoldScopeInstance.align
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.ScrollStrategy
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.rememberCollapsingToolbarScaffoldState
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.features.detail_account.views.AccountNameText
import com.finance_tracker.finance_tracker.features.detail_account.views.DetailAccountAppBar
import com.finance_tracker.finance_tracker.features.detail_account.views.DetailAccountExpandedAppBar
import com.finance_tracker.finance_tracker.features.detail_account.views.EditButton
import dev.icerock.moko.resources.compose.painterResource
import org.koin.core.parameter.parametersOf

@Composable
@Suppress("MagicNumber")
internal fun DetailAccountScreen(
    account: Account
) {
    ComposeScreen<DetailAccountViewModel>(
        parameters = { parametersOf(account) }
    ) { viewModel ->
        UpdateSystemBarsConfigEffect {
            isStatusBarLight = false
        }

        val transactions = viewModel.paginatedTransactions.collectAsLazyPagingItems()
        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage, transactions)
        }

        LaunchedEffect(Unit) {
            viewModel.onScreenViewed()
        }

        val accountData by viewModel.accountData.collectAsState()
        val state = rememberCollapsingToolbarScaffoldState()
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = state,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                var editButtonPositionX by remember { mutableStateOf(0) }
                DetailAccountAppBar(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = 1f - state.toolbarState.progress
                        },
                    color = accountData.colorModel.color.toUIColor()
                )
                DetailAccountExpandedAppBar(
                    modifier = Modifier
                        .parallax(0.4f),
                    contentAlpha = state.toolbarState.progress,
                    color = accountData.colorModel.color.toUIColor(),
                    amount = accountData.balance,
                    icon = accountData.icon,
                    editButtonPositionX = editButtonPositionX,
                    onIconClick = viewModel::onIconClick
                )

                AppBarIcon(
                    modifier = Modifier
                        .padding(
                            horizontal = 4.dp,
                            vertical = 6.dp
                        )
                        .statusBarsPadding()
                        .align(Alignment.TopStart),
                    painter = painterResource(MR.images.ic_arrow_back),
                    onClick = viewModel::onBackClick,
                    tint = CoinTheme.color.white
                )

                AccountNameText(
                    modifier = Modifier.widthIn(max = editButtonPositionX.toDp()),
                    name = accountData.name,
                    state = state
                )

                EditButton(
                    modifier = Modifier.onGloballyPositioned {
                        editButtonPositionX = it.positionInParent().x.toInt()
                    },
                    state = state,
                    tint = accountData.colorModel.color.toUIColor(),
                    onClick = viewModel::onEditClick
                )
            }
        ) {
            val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
            CommonTransactionsList(
                modifier = Modifier
                    .background(CoinTheme.color.background),
                transactions = transactions,
                contentPadding = PaddingValues(
                    bottom = navigationBarsHeight
                ),
                onClick = {
                    viewModel.onTransactionClick(transaction = it.transaction)
                },
                onAddTransactionClick = viewModel::onAddTransactionClick,
                stubHeightAlignment = 0.6f,
            )
        }
    }
}