package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import dev.icerock.moko.resources.compose.painterResource
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable

private val HorizontalPadding = 16.dp

@Composable
internal fun CoinWidget(
    title: String,
    modifier: Modifier = Modifier,
    withBorder: Boolean = false,
    withHorizontalPadding: Boolean = true,
    onClick: (() -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = {}
) {
    val clickEnabled = onClick != null
    Column(
        modifier = modifier
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .scaleClickAnimation(enabled = clickEnabled)
                .`if`(clickEnabled) {
                    noRippleClickable { onClick?.invoke() }
                }
                .padding(
                    vertical = 8.dp,
                    horizontal = HorizontalPadding
                ),
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                modifier = Modifier.heightIn(min = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = CoinTheme.typography.h5
                )
                if (action != null) {
                    Spacer(modifier = Modifier.weight(1f))
                    action()
                }
            }
            if (onClick != null) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(MR.images.ic_arrow_right_small),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }

        val shape by remember(withBorder) {
            derivedStateOf {
                if (withBorder) {
                    RoundedCornerShape(12.dp)
                } else {
                    RoundedCornerShape(0.dp)
                }
            }
        }
        Box(
            modifier = Modifier
                .`if`(withHorizontalPadding) {
                    padding(horizontal = HorizontalPadding)
                }
                .border(
                    width = if (withBorder) 1.dp else 0.dp,
                    color = if (withBorder) CoinTheme.color.dividers else Color.Transparent,
                    shape = shape
                )
                .clip(shape)
                .background(CoinTheme.color.background)
        ) {
            content()
        }
    }
}