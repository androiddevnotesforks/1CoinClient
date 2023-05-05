package com.finance_tracker.finance_tracker.features.plans.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun PlansTopBar(
    modifier: Modifier = Modifier
) {
    CoinTopAppBar(
        modifier = modifier,
        appBarHeight = 64.dp,
        title = {
            Text(
                text = stringResource(MR.strings.plans_screen_topbar_text),
                style = CoinTheme.typography.h4,
                color = CoinTheme.color.content
            )
        }
    )
}