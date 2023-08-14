package com.finance_tracker.finance_tracker.features.settings.views.items

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.ui.CoinSwitch
import com.finance_tracker.finance_tracker.features.settings.views.ListItem
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Suppress("UnusedPrivateMember")
@Composable
internal fun SettingsSendingUsageDataItem(
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onChange: (isEnabled: Boolean) -> Unit = {},
    onInfoClick: () -> Unit = {}
) {
    ListItem(
        modifier = modifier,
        iconLeftPainter = painterResource(MR.images.ic_data_sending),
        text = stringResource(MR.strings.settings_sending_usage_data),
        onInfoClick = onInfoClick,
        iconRight = {
            CoinSwitch(
                modifier = Modifier.padding(end = 16.dp),
                value = isEnabled,
                onChange = onChange
            )
        }
    )
}