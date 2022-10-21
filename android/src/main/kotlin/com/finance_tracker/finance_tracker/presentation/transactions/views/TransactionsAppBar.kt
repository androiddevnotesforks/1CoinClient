package com.finance_tracker.finance_tracker.presentation.transactions.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.loadXmlPicture

@Composable
fun TransactionsAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier
            .background(CoinTheme.color.background)
            .statusBarsPadding(),
        title = {
            Text(
                text = stringResource(R.string.transactions_title),
                style = CoinTheme.typography.h4
            )
        },
        actions = {
            AppBarIcon(painter = rememberVectorPainter(loadXmlPicture("ic_more_vert")))
        },
        backgroundColor = Color.White
    )
}