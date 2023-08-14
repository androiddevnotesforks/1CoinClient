package com.finance_tracker.finance_tracker.core.ui.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun CoinSnackbar(
    snackbarState: SnackbarState,
    modifier: Modifier = Modifier
) {
    val accentColor = when (snackbarState) {
        is SnackbarState.Error -> CoinTheme.color.accentRed
        is SnackbarState.Success -> CoinTheme.color.accentGreen
        is SnackbarState.Information -> CoinTheme.color.primary
    }
    val shape = RoundedCornerShape(12.dp)

    Surface(
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = accentColor,
                shape = shape
            ),
        shape = shape,
        elevation = 6.dp,
        color = CoinTheme.color.background,
        contentColor = CoinTheme.color.content
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .padding(vertical = 2.dp)
                    .size(24.dp),
                painter = painterResource(snackbarState.iconResId),
                contentDescription = null,
                tint = accentColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(snackbarState.textResId),
                style = CoinTheme.typography.body1,
                color = CoinTheme.color.content
            )

            SnackbarAction(
                actionState = snackbarState.actionState
            )
        }
    }
}

@Composable
private fun SnackbarAction(
    actionState: SnackbarActionState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(start = 8.dp)
    ) {
        when (actionState) {
            is SnackbarActionState.Close -> {
                Icon(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .clip(CircleShape)
                        .clickable { actionState.onAction() }
                        .size(32.dp)
                        .padding(6.dp),
                    painter = painterResource(MR.images.ic_close),
                    contentDescription = null,
                    tint = CoinTheme.color.content
                )
            }
            is SnackbarActionState.Undo -> {
                Row(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .widthIn(min = 32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { actionState.onAction() }
                        .background(CoinTheme.color.secondaryBackground)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(MR.images.ic_undo),
                        contentDescription = null,
                        tint = CoinTheme.color.content
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(MR.strings.toast_action),
                        style = CoinTheme.typography.body1,
                        color = CoinTheme.color.content
                    )
                }
            }
        }
    }
}