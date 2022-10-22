package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun AnalyticsScreenAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier
            .background(CoinTheme.color.background),
        title = {
            Text(
                text = stringResource(R.string.tab_analytics),
                style = CoinTheme.typography.h4
            )
        },
        backgroundColor = CoinTheme.color.background
    )
}

@Preview
@Composable
fun AnalyticsScreenAppBarPreview() {
    AnalyticsScreenAppBar()
}