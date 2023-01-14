package com.finance_tracker.finance_tracker.presentation.settings_sheet.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Composable
internal fun SettingsSheetVersionAndUserIdInfo(
    versionName: String,
    userId: String,
    onCopyUserId: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = "${stringResource("version")} $versionName",
            style = CoinTheme.typography.subtitle4,
            color = CoinTheme.color.secondary
        )
        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .clip(RoundedCornerShape(16.dp))
                .clickable { onCopyUserId.invoke() }
                .padding(
                    vertical = 8.dp,
                    horizontal = 8.dp
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1f)
                    .widthIn(max = 120.dp),
                text = "${stringResource("user_id")} $userId",
                style = CoinTheme.typography.subtitle4,
                color = CoinTheme.color.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                painter = rememberVectorPainter("ic_duplicate"),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(12.dp),
                tint = CoinTheme.color.secondary
            )
        }
    }
}