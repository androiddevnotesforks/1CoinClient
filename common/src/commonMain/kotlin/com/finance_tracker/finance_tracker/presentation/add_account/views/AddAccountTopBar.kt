package com.finance_tracker.finance_tracker.presentation.add_account.views

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun AddAccountTopBar(
    topBarTextId: String,
    modifier: Modifier = Modifier,
) {
    val rootController = LocalRootController.current
    TopAppBar(
        modifier = modifier,
        backgroundColor = CoinTheme.color.background,
        contentColor = CoinTheme.color.content,
        navigationIcon = {
            AppBarIcon(
                painter = rememberVectorPainter("ic_arrow_back"),
                onClick = { rootController.popBackStack() }
            )
        },
        title = {
            Text(
                text = stringResource(topBarTextId),
                style = CoinTheme.typography.h4
            )
        }
    )
}