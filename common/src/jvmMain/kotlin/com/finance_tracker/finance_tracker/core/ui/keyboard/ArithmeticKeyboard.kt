package com.finance_tracker.finance_tracker.core.ui.keyboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.keyboard.KeyboardAction
import com.finance_tracker.finance_tracker.core.common.keyboard.arithmeticActions
import com.finance_tracker.finance_tracker.core.common.keyboard.numPadActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.DefaultRippleTheme
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

private const val KeyboardRowSize = 3

@Composable
internal fun ArithmeticKeyboard(
    shouldShowAmountKeyboard: Boolean,
    onKeyboardClick: (KeyboardAction) -> Unit,
    modifier: Modifier = Modifier,
    onKeyboardClose: () -> Unit = {},
    isBottomExpandable: Boolean = true,
    hasElevation: Boolean = isBottomExpandable,
    hasNavBarInsets: Boolean = isBottomExpandable,
    isArithmeticOperationsVisible: Boolean = true,
    hasBackground: Boolean = true
) {
    val navBarInsets = LocalFixedInsets.current.navigationBarsHeight
    val keyboardBodyRows = numPadActions.chunked(KeyboardRowSize)
    val slidingAnimationSpec = spring(
        stiffness = Spring.StiffnessMedium,
        visibilityThreshold = IntOffset.VisibilityThreshold
    )

    AnimatedVisibility(
        enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMedium)) +
                slideInVertically(
                    animationSpec = slidingAnimationSpec,
                    initialOffsetY = { it / 2 }
                ),
        exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMedium)) +
                slideOutVertically(
                    animationSpec = slidingAnimationSpec,
                    targetOffsetY = { it / 2 }
                ),
        visible = shouldShowAmountKeyboard,
        modifier = modifier
    ) {
        Surface(
            color = if (hasBackground) {
                CoinTheme.color.secondaryBackground
            } else {
                Color.Transparent
            },
            elevation = if (hasElevation) 8.dp else 0.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                CompositionLocalProvider(LocalRippleTheme provides DefaultRippleTheme) {
                    if (isArithmeticOperationsVisible) {
                        KeyboardGridRow(Modifier.height(44.dp)) {
                            arithmeticActions.forEach {
                                KeyboardGridElement(
                                    keyboardAction = it,
                                    onClick = { onKeyboardClick(it) })
                            }

                            if (isBottomExpandable) {
                                KeyboardCloseIcon(onClick = onKeyboardClose)
                            }
                        }
                    }

                    keyboardBodyRows.forEach {
                        KeyboardGridRow(Modifier.weight(1f)) {
                            it.forEach {
                                KeyboardGridElement(
                                    keyboardAction = it,
                                    onClick = { onKeyboardClick(it) }
                                )
                            }
                        }
                    }
                }

                if (hasNavBarInsets) {
                    Spacer(Modifier.height(navBarInsets))
                }
            }
        }
    }
}

@Composable
private fun KeyboardGridRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        content()
    }
}

@Composable
private fun RowScope.KeyboardGridElement(
    keyboardAction: KeyboardAction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .weight(1F)
            .fillMaxHeight()
            .clickable { onClick() }
    ) {
        when (keyboardAction) {
            is KeyboardAction.Operation -> KeyboardGridChar(keyboardAction.operation)
            is KeyboardAction.Digit -> KeyboardGridChar(keyboardAction.digit)
            is KeyboardAction.NumberSeparator -> KeyboardGridChar(keyboardAction.char)
            KeyboardAction.Delete -> KeyboardGridIcon(MR.images.ic_backspace)
        }
    }
}

@Composable
private fun KeyboardGridChar(
    char: String
) {
    Text(
        text = char,
        style = CoinTheme.typography.h3,
        color = CoinTheme.color.content,
    )
}

@Composable
private fun KeyboardGridIcon(
    iconRes: ImageResource
) {
    Icon(
        painter = painterResource(iconRes),
        contentDescription = "Icon",
        tint = CoinTheme.color.content,
        modifier = Modifier.size(24.dp)
    )
}

@Composable
private fun RowScope.KeyboardCloseIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .weight(1F)
            .fillMaxHeight()
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(MR.images.ic_close_keyboard),
            contentDescription = "Icon",
            tint = CoinTheme.color.primary,
            modifier = Modifier.size(34.dp)
        )
    }
}
