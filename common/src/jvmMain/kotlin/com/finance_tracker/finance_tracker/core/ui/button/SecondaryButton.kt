package com.finance_tracker.finance_tracker.core.ui.button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.BaseButton

@Composable
@Suppress("ReusedModifierInstance")
fun SecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    BaseButton(
        modifier = modifier,
        contentColor = CoinTheme.color.content,
        backgroundColor = Color.Transparent,
        borderColor = CoinTheme.color.dividers,
        loading = loading,
        enabled = enabled,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = CoinTheme.typography.body1_medium,
            textAlign = TextAlign.Center,
            color = CoinTheme.color.content
        )
    }
}
