package com.finance_tracker.finance_tracker.core.ui.tab_rows

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.common.toDp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.NoRippleTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.core.ui.pagerTabIndicatorOffset
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay

private const val TabIndicationAnimDuration = 250L

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CoinTabRow(
    selectedTabIndex: Int,
    data: ImmutableList<String>,
    modifier: Modifier = Modifier,
    maxTabsWidth: Dp = Int.MAX_VALUE.dp,
    hasBottomDivider: Boolean = true,
    isHorizontallyCentered: Boolean = false,
    pagerState: PagerState? = null,
    onTabSelect: (index: Int) -> Unit = {}
) {
    var totalWidth by remember { mutableStateOf(0) }
    val tabsWidth = remember { mutableMapOf<Int, Int>() }
    val centeredEdgePadding by remember(totalWidth, tabsWidth) {
        derivedStateOf {
            (totalWidth - tabsWidth.values.sum())
                .coerceAtLeast(0)
                .toFloat() / 2f
        }
    }

    var alpha by remember { mutableStateOf(0f) }
    val alphaAnimatable by animateFloatAsState(
        alpha,
        animationSpec = tween(durationMillis = 150)
    )
    LaunchedEffect(Unit) {
        // Skips indication animation after centering tabs
        delay(TabIndicationAnimDuration)
        alpha = 1f
    }

    Column(
        modifier = modifier
            .background(CoinTheme.color.backgroundSurface)
            .alpha(
                alpha = if (isHorizontallyCentered) {
                    alphaAnimatable
                } else {
                    1f
                }
            )
    ) {
        ScrollableTabRow(
            modifier = Modifier
                .widthIn(max = maxTabsWidth)
                .`if`(isHorizontallyCentered) {
                    onSizeChanged {
                        totalWidth = it.width
                    }
                },
            edgePadding = centeredEdgePadding.toInt().toDp(),
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.Transparent,
            contentColor = CoinTheme.color.primary,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = if (pagerState != null) {
                        Modifier
                            .pagerTabIndicatorOffset(pagerState, tabPositions)
                    } else {
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    }.padding(horizontal = 16.dp),
                    height = 2.dp
                )
            },
            divider = {
                TabRowDefaults.Divider(thickness = 0.dp)
            },
            tabs = {
                data.forEachIndexed { index, _ ->
                    CoinTab(
                        modifier = Modifier
                            .`if`(isHorizontallyCentered) {
                                onSizeChanged {
                                    tabsWidth[index] = it.width
                                }
                            },
                        text = data[index],
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelect(index) }
                    )
                }
            }
        )

        if (hasBottomDivider) {
            TabRowDefaults.Divider(
                color = CoinTheme.color.dividers
            )
        }
    }
}

@Suppress("ReusedModifierInstance")
@Composable
private fun CoinTab(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Tab(
            modifier = modifier,
            selected = selected,
            onClick = onClick,
            text = {
                Text(
                    text = text,
                    style = CoinTheme.typography.body1_medium.staticTextSize(),
                    color = if (selected) {
                        CoinTheme.color.primary
                    } else {
                        CoinTheme.color.secondary
                    }
                )
            }
        )
    }
}