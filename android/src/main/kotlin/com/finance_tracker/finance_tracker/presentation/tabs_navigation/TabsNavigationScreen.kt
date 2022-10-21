package com.finance_tracker.finance_tracker.presentation.tabs_navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.compose.rememberNavController
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.loadXmlPicture
import com.finance_tracker.finance_tracker.presentation.NavGraphs
import com.finance_tracker.finance_tracker.presentation.destinations.AddTransactionScreenDestination
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
                    painter = rememberVectorPainter(loadXmlPicture("ic_plus")),
                    contentDescription = null
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.tab
        )
    }
}