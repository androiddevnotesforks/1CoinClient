package com.finance_tracker.finance_tracker.presentation.tabs_navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.presentation.NavGraphs
import com.finance_tracker.finance_tracker.presentation.destinations.AddTransactionScreenDestination
import com.finance_tracker.finance_tracker.presentation.home.TopBar
import com.finance_tracker.finance_tracker.theme.CoinTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun TabsNavigationScreen(
    navigator: DestinationsNavigator
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            BottomNavigationBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = CoinTheme.color.primary,
                contentColor = CoinTheme.color.primaryVariant,
                onClick = {
                    navigator.navigate(AddTransactionScreenDestination)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = null
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        topBar = { TopBar() }
    ) {
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.tab
        )
    }
}