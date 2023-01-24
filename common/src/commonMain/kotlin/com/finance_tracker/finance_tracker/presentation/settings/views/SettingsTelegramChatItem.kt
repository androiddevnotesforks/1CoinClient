package com.finance_tracker.finance_tracker.presentation.settings.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsTelegramChatItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = 12.dp,)
            .fillMaxWidth(),
    ) {
        Image(
            painter = rememberVectorPainter("telegram"),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 8.dp
                )
                .size(24.dp)
                .align(Alignment.CenterVertically),
        )
        Text(
            text = stringResource(MR.strings.settings_telegram_community),
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically),
            style = CoinTheme.typography.body1_medium
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .padding(end = 16.dp)
                .clip(CircleShape)
                .clickable { onClick.invoke() }
                .padding(4.dp),
            painter = rememberVectorPainter("ic_link"),
            contentDescription = null,
            tint = CoinTheme.color.content
        )
    }
}