package com.finance_tracker.finance_tracker.presentation.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.theme.CoinTheme

@Composable
fun AccountsAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier
            .background(CoinTheme.color.background)
            .statusBarsPadding(),
        title = {
            Text(
                text = stringResource(R.string.accounts_screen_header),
                style = CoinTheme.typography.h4
            )
        },
        actions = {
            AppBarIcon(painter = painterResource(R.drawable.ic_plus),
                tint = Color(0xFF009BFF)
            )
        },
        backgroundColor = Color.White
    )
}

@Preview
@Composable
fun AccountsAppBarPreview() {
    AccountsAppBar()
}