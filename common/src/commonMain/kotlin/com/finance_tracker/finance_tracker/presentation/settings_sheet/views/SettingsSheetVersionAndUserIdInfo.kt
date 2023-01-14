package com.finance_tracker.finance_tracker.presentation.settings_sheet.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Composable
fun SettingsSheetVersionAndUserIdInfo(
    versionNumber: String,
    userId: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${stringResource("version")} $versionNumber",
            style = CoinTheme.typography.subtitle4,
            color = CoinTheme.color.secondary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier
                .padding(end = 4.dp),
            text = "${stringResource("user_id")} $userId",
            style = CoinTheme.typography.subtitle4,
            color = CoinTheme.color.secondary
        )
        Icon(
            painter = rememberVectorPainter("ic_duplicate"),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(12.dp)
                .clickable {  },
            tint = CoinTheme.color.secondary
        )
    }
}