package com.finance_tracker.finance_tracker.features.transactions.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun TransactionsAppBar(
    modifier: Modifier = Modifier,
    selectedItemsCount: Int = 0,
    onCloseClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val hasSelectedItems by remember(selectedItemsCount) {
        derivedStateOf { selectedItemsCount > 0 }
    }
    CoinTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = if (hasSelectedItems) {
                    stringResource(MR.strings.transactions_title_selected) + " " + selectedItemsCount
                } else {
                    stringResource(MR.strings.transactions_title_normal)
                },
                style = CoinTheme.typography.h4,
                color = CoinTheme.color.content,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = if (hasSelectedItems) {{
            AppBarIcon(
                painter = painterResource(MR.images.ic_close),
                onClick = onCloseClick,
            )
        }} else null,
        actions = {
            if (hasSelectedItems) {
                AppBarIcon(
                    painter = painterResource(MR.images.ic_delete),
                    onClick = onDeleteClick,
                    tint = CoinTheme.color.accentRed
                )
            }
        }
    )
}