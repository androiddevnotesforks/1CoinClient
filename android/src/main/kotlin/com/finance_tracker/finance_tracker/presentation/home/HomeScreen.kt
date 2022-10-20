package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.navigation.TabNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.koin.androidx.compose.getViewModel

@TabNavGraph(start = true)
@Destination
@Composable

fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeScreenViewModel = getViewModel()
) {

    val accounts by viewModel.accounts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        HomeTopBar(navigator)

        MyAccountsHeader(
            modifier = Modifier
                .padding(top = 26.dp)
        )

        AccountsWidget(data = accounts)

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(EmptyDestinationsNavigator)
}