package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar

@Composable
fun AnalyticsScreenAppBar(modifier: Modifier = Modifier) {
    CoinTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource("tab_analytics"),
                style = CoinTheme.typography.h4
            )
        }
    )
}