package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.painterDescResource
import com.finance_tracker.finance_tracker.core.common.stringDescResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.NoRippleTheme
import com.finance_tracker.finance_tracker.features.tabs_navigation.TabsNavigationComponent

@Composable
internal fun BottomNavigationBar(
    allTabs: List<TabsNavigationComponent.Config?>,
    activeChild: TabsNavigationComponent.Child,
    onItemSelect: (tab: TabsNavigationComponent.Config) -> Unit,
    modifier: Modifier = Modifier,
) {
    val activeTab = getTabForChild(activeChild)
    val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight
    BottomAppBar(
        modifier = modifier,
        backgroundColor = CoinTheme.color.backgroundSurface,
        elevation = 12.dp,
        contentColor = CoinTheme.color.primary,
        contentPadding = PaddingValues(
            bottom = navigationBarsHeight
        )
    ) {
        allTabs.forEach { tab ->
            if (tab != null) {
                BottomNavigationItem(
                    item = tab,
                    selected = tab == activeTab,
                    onClick = { onItemSelect(tab) }
                )
            } else {
                EmptyBottomNavigationItem()
            }
        }
    }
}

private fun getTabForChild(child: TabsNavigationComponent.Child): TabsNavigationComponent.Config {
    return when (child) {
        is TabsNavigationComponent.Child.Analytics -> {
            TabsNavigationComponent.Config.Analytics
        }
        is TabsNavigationComponent.Child.Home -> {
            TabsNavigationComponent.Config.Home
        }
        is TabsNavigationComponent.Child.Plans -> {
            TabsNavigationComponent.Config.Plans
        }
        is TabsNavigationComponent.Child.Transactions -> {
            TabsNavigationComponent.Config.Transactions
        }
    }
}

@Composable
private fun EmptyBottomNavigationItem() {
    Box(modifier = Modifier.width(46.dp))
}

@Composable
private fun RowScope.BottomNavigationItem(
    item: TabsNavigationComponent.Config,
    selected: Boolean,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        BottomNavigationItem(
            icon = {
                NavBarIcon(
                    selected = selected,
                    selectedIcon = painterDescResource(item.selectedIcon),
                    unselectedIcon = painterDescResource(item.unselectedIcon),
                    contentDescription = stringDescResource(item.title)
                )
            },
            label = {
                Text(
                    text = stringDescResource(item.title),
                    style = CoinTheme.typography.subtitle4,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            selectedContentColor = CoinTheme.color.primary,
            unselectedContentColor = CoinTheme.color.secondary,
            alwaysShowLabel = true,
            selected = selected,
            onClick = {
                onClick()
            }
        )
    }
}

@Composable
private fun NavBarIcon(
    selected: Boolean,
    selectedIcon: Painter,
    unselectedIcon: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        AnimatedVisibility(visible = selected, enter = fadeIn(), exit = fadeOut()) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = selectedIcon,
                contentDescription = contentDescription
            )
        }
        AnimatedVisibility(visible = !selected, enter = fadeIn(), exit = fadeOut()) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = unselectedIcon,
                contentDescription = contentDescription
            )
        }
    }
}