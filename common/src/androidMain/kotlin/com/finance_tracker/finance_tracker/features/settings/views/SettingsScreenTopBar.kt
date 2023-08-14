package com.finance_tracker.finance_tracker.features.settings.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsScreenTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    CoinTopAppBar(
        modifier = modifier,
        appBarHeight = 56.dp,
        navigationIcon = {
            AppBarIcon(
                painter = painterResource(MR.images.ic_arrow_back),
                onClick = onBackClick,
            )
        },
        title = {
            Text(
                text = stringResource(MR.strings.settings_topbar_text),
                style = CoinTheme.typography.h4,
                color = CoinTheme.color.content,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    )
}