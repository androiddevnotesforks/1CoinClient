package com.finance_tracker.finance_tracker.core.ui.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun ActionButton(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = CoinTheme.color.content,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp),
        text = text,
        style = CoinTheme.typography.body1_medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = textColor,
        textAlign = TextAlign.Center
    )
}