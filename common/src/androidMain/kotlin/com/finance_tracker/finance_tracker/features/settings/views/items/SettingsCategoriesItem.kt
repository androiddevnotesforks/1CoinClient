package com.finance_tracker.finance_tracker.features.settings.views.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.features.settings.views.ListItem
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsCategoriesItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        iconLeftPainter = painterResource(MR.images.ic_categories),
        text = stringResource(MR.strings.settings_categories),
        onClick = onClick
    )
}