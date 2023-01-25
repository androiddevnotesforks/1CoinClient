package com.finance_tracker.finance_tracker.presentation.settings.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource


@Composable
internal fun SettingsMyProfileItem(
    userEmail: String,
    isUserAuthorized: Boolean,
    modifier: Modifier = Modifier,
    onLogOutClick: () -> Unit = {},
    onLogInClick: () -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .`if`(!isUserAuthorized) {
                    clickable { onLogInClick.invoke() }
                }
                .padding(
                    top = 28.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp),
                painter = rememberVectorPainter("ic_user"),
                contentDescription = null,
                tint = CoinTheme.color.content,
            )

            Text(
                text = if (isUserAuthorized) {
                    userEmail
                } else {
                    stringResource(MR.strings.settings_authorization)
                },
                style = CoinTheme.typography.body1_medium,
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .`if`(isUserAuthorized) {
                        clickable { onLogOutClick.invoke() }
                    }
                    .padding(4.dp),
                painter = if (isUserAuthorized) {
                    rememberVectorPainter("ic_logout")
                } else {
                    rememberVectorPainter("ic_arrow_authorize")
                },
                contentDescription = null,
                tint = CoinTheme.color.content
            )
        }
    }
}