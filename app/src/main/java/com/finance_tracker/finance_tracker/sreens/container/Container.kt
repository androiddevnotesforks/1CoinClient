package com.finance_tracker.finance_tracker.sreens.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance_tracker.finance_tracker.BottomNavigationBar
import com.finance_tracker.finance_tracker.NavigationItem
import com.finance_tracker.finance_tracker.sreens.main.MainScreen
import com.finance_tracker.finance_tracker.sreens.more.MoreScreen
import com.finance_tracker.finance_tracker.sreens.operations.OperationsScreen

@Composable
fun Container(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        content = { paddingValues ->
            Box(modifier = modifier.padding(paddingValues)) {
                Navigation(navHostController = navController)
            }
        }

    )
}

@Composable
fun Navigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navHostController, startDestination = NavigationItem.Main.route) {
        composable(NavigationItem.Main.route) {
            MainScreen()
        }
        composable(NavigationItem.Operations.route) {
            OperationsScreen()
        }
        composable(NavigationItem.Else.route) {
            MoreScreen()
        }
    }
}