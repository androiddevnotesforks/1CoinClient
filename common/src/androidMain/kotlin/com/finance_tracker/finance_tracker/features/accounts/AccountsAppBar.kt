package com.finance_tracker.finance_tracker.features.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun AccountsAppBar(
    onAddAccountClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CoinTopAppBar(
        modifier = modifier,
        navigationIcon = {
            AppBarIcon(
                painter = painterResource(MR.images.ic_arrow_back),
                onClick = onBackClick,
            )
        },
        title = {
            Text(
                text = stringResource(MR.strings.accounts_screen_header),
                style = CoinTheme.typography.h4,
                color = CoinTheme.color.content,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onAddAccountClick() }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(24.dp),
                    painter = painterResource(MR.images.ic_plus),
                    contentDescription = null,
                    tint = CoinTheme.color.primary,
                )
                Text(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    text = stringResource(MR.strings.accounts_screen_add_account),
                    style = CoinTheme.typography.subtitle1,
                    color = CoinTheme.color.primary,
                )
            }
        }
    )
}