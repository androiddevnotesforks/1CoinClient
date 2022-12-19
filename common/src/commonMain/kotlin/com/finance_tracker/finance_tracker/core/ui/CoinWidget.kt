package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

private val HorizontalPadding = 16.dp

@Composable
fun CoinWidget(
    title: String,
    modifier: Modifier = Modifier,
    withBorder: Boolean = false,
    withHorizontalPadding: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .`if`(onClick != null) {
                clickable { onClick?.invoke() }
            }
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = HorizontalPadding),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = title,
                style = CoinTheme.typography.h5
            )
            if (onClick != null) {
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = rememberVectorPainter("ic_arrow_next_small"),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }

        Box(
            modifier = Modifier
                .`if`(withHorizontalPadding) {
                    padding(horizontal = HorizontalPadding)
                }
                .padding(top = 12.dp)
                .`if`(withBorder) {
                    border(
                        width = 1.dp,
                        color = CoinTheme.color.dividers,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
        ) {
            content.invoke()
        }
    }
}