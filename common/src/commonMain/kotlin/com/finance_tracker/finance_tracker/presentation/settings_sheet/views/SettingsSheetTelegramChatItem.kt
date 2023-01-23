package com.finance_tracker.finance_tracker.presentation.settings_sheet.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingSheetTelegramChatItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(vertical = 12.dp),
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
            style = CoinTheme.typography.body1
        )
    }
}