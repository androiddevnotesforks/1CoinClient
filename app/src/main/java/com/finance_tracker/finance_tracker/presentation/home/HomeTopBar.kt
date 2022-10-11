package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.theme.AppColors
import com.finance_tracker.finance_tracker.theme.CoinTheme

@Composable
fun HomeTopBar() {
    TopAppBar(
        backgroundColor = AppColors.White,
//        contentColor = CoinTheme.color.primary,
        title = {
            Column {
                Text(
                    text = "$42 520",
                    style = CoinTheme.typography.h4
                )
                Text(
                    text = stringResource(R.string.home_topbar_text),
                    style = CoinTheme.typography.subtitle2
                )
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .background(
                        AppColors.Teal700,
                        shape = CircleShape
                    )
                    .padding(9.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = null,
                    Modifier
                        .alpha(0.8f)
                        .clickable { },
                    tint = AppColors.Black
                )
            }
        },
    )
}

@Composable
@Preview
fun HomeTopBarPreview() {
    HomeTopBar()
}
