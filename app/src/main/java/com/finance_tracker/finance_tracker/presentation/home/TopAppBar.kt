package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.theme.AppColors
import com.finance_tracker.finance_tracker.theme.CoinTheme

@Composable
fun TopBar() {
    TopAppBar(
        backgroundColor = AppColors.White,
        contentColor = CoinTheme.color.primary,
        title = {
            Column {
                Text(
                    text = stringResource(R.string.topbar_title),
                    style = CoinTheme.typography.h4
                )
                Text(
                    text = stringResource(R.string.topbar_balance),
                    style = CoinTheme.typography.subtitle2
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_settings),
                    contentDescription = null
                )
            }
        },
        elevation = 10.dp
    )
}

@Composable
@Preview
fun PreviewTopBar() {
    TopBar()
}
