package com.finance_tracker.finance_tracker.features.plans.overview.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun PlansOverviewTopBar(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CoinTopAppBar(
            appBarHeight = 64.dp,
            title = {
                Text(
                    text = stringResource(MR.strings.plans_title),
                    style = CoinTheme.typography.h4,
                    color = CoinTheme.color.content
                )
            }
        )
    }
}