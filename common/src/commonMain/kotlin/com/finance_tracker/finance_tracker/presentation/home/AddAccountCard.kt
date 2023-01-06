package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Suppress("MagicNumber")
@Composable
fun AddAccountCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(38.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = CoinTheme.color.dividers,
                shape = RoundedCornerShape(12.dp)
            )
            .background(CoinTheme.color.secondaryBackground)
            .clickable { onClick.invoke() },
    ) {
        Icon(
            painter = rememberVectorPainter("ic_plus"),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Center),
            tint = CoinTheme.color.content.copy(alpha = 0.5f)
        )
    }
}