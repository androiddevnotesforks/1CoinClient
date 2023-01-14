package com.finance_tracker.finance_tracker.presentation.settings_sheet.views

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
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinSwitch
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Suppress("UnusedPrivateMember")
@Composable
internal fun SettingsSheetSendingUsageDataItem(
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onChange: (isEnabled: Boolean) -> Unit = {},
    onInfoClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
    ) {
        Icon(
            painter = rememberVectorPainter(id = "ic_usage_sending"),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 8.dp
                )
                .size(24.dp)
                .align(Alignment.CenterVertically),
            tint = CoinTheme.color.content
        )
        Text(
            text = stringResource("settings_sending_usage_data"),
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = CoinTheme.typography.body1
        )
        Icon(
            painter = rememberVectorPainter(id = "ic_more_info"),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 1.dp)
                .size(24.dp)
                .align(Alignment.CenterVertically)
                .clip(CircleShape)
                .clickable { onInfoClick.invoke() }
                .padding(4.dp),
            tint = CoinTheme.color.content
        )
        Spacer(modifier = Modifier.weight(1f))
        CoinSwitch(
            modifier = Modifier
                .padding(end = 16.dp)
                .clip(CircleShape),
            value = isEnabled,
            onChange = onChange
        )
    }
}