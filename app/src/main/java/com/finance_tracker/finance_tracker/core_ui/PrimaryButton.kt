package com.finance_tracker.finance_tracker.core_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.theme.CoinTheme

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    CompositionLocalProvider(
        LocalContentColor provides CoinTheme.color.primaryVariant
    ) {
        Text(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(CoinTheme.color.primary)
                .clickable { onClick.invoke() }
                .padding(12.dp),
            text = text,
            style = CoinTheme.typography.body2_medium,
            textAlign = TextAlign.Center,
            color = LocalContentColor.current
        )
    }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(text = "Add")
}