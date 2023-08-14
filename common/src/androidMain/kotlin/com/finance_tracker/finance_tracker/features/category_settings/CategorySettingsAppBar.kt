package com.finance_tracker.finance_tracker.features.category_settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinTxTypeTopAppBar
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CategorySettingsAppBar(
    selectedTransactionTypeTab: TransactionTypeTab,
    pagerState: PagerState,
    onTransactionTypeSelect: (TransactionTypeTab) -> Unit,
    onAddCategoryClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CoinTxTypeTopAppBar(
        pagerState = pagerState,
        modifier = modifier,
        navigationIcon = {
            AppBarIcon(
                painter = painterResource(MR.images.ic_arrow_back),
                onClick = onBackClick,
            )
        },
        title = {
            Text(
                text = stringResource(MR.strings.category_settings_title),
                style = CoinTheme.typography.h4,
                color = CoinTheme.color.content,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            AppBarIcon(
                painterResource(MR.images.ic_plus),
                onClick = onAddCategoryClick,
                tint = CoinTheme.color.primary,
            )
        },
        selectedTransactionTypeTab = selectedTransactionTypeTab,
        onTransactionTypeSelect = onTransactionTypeSelect
    )
}