package com.finance_tracker.finance_tracker.features.settings.views.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.toDp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.ThemeMode
import com.finance_tracker.finance_tracker.features.settings.views.ListItem
import com.finance_tracker.finance_tracker.features.settings.views.dropdown_menus.ThemesDropdownMenu
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SettingsThemeItem(
    themes: List<ThemeMode>,
    themeMode: ThemeMode,
    onClick: (themeMode: ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val currencyMenuExpanded = remember { mutableStateOf(false) }
    val menuOffsetX = remember { mutableStateOf(0) }
    val menuOffsetY = remember { mutableStateOf(0) }
    val onChooseThemeClick = remember { { currencyMenuExpanded.value = true } }

    Box(
        modifier = modifier
    ) {
        ListItem(
            modifier = Modifier
                .onGloballyPositioned {
                    menuOffsetX.value = it.positionInParent().x.toInt() + it.size.width
                    menuOffsetY.value = it.positionInParent().y.toInt() - it.size.height
                },
            iconLeftPainter = painterResource(MR.images.ic_theme),
            text = stringResource(MR.strings.settings_theme),
            iconRight = {
                Row(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onChooseThemeClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(themeMode.text),
                        color = CoinTheme.color.primary,
                        style = CoinTheme.typography.body1_medium
                    )
                    Icon(
                        painter = painterResource(MR.images.ic_arrow_down),
                        contentDescription = null,
                        tint = CoinTheme.color.primary
                    )
                }
            }
        )

        ThemesDropdownMenu(
            items = themes,
            expandedState = currencyMenuExpanded,
            onThemeSelect = onClick,
            xOffset = menuOffsetX.value.toDp() - 124.dp,
            yOffset = menuOffsetY.value.toDp()
        )
    }
}