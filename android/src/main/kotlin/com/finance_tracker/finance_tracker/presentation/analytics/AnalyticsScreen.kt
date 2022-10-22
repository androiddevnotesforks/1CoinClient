package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.navigation.TabNavGraph
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.ExpenseIncomeTabs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@TabNavGraph
@Destination
@Composable
fun AnalyticsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoinTheme.color.background)
            .statusBarsPadding()
    ) {

        AnalyticsScreenAppBar()

        ExpenseIncomeTabs(
            navigator = EmptyDestinationsNavigator,
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    top = 24.dp
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnalyticsScreenPreview() {
    AnalyticsScreen()
}