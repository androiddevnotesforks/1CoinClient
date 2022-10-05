package com.finance_tracker.finance_tracker.sreens.tabs_navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.finance_tracker.finance_tracker.sreens.NavGraphs
import com.finance_tracker.finance_tracker.sreens.appCurrentDestinationAsState
import com.finance_tracker.finance_tracker.sreens.destinations.Destination
import com.finance_tracker.finance_tracker.sreens.startAppDestination
import com.finance_tracker.finance_tracker.theme.CoinTheme
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.tab.startAppDestination

    BottomAppBar(
        modifier = modifier,
        backgroundColor = CoinTheme.color.background,
        contentColor = CoinTheme.color.primary,
        cutoutShape = CircleShape
    ) {
        BottomNavigationItems.list.forEach { item ->
            if (item != null) {
                BottomNavigationItem(
                    navController = navController,
                    item = item,
                    isSelected = currentDestination == item.direction
                )
            } else {
                EmptyBottomNavigationItem()
            }
        }
    }
}

@Composable
private fun EmptyBottomNavigationItem() {
    Box(modifier = Modifier.width(48.dp))
}

@Composable
private fun RowScope.BottomNavigationItem(
    navController: NavController,
    item: BottomNavigationItem,
    isSelected: Boolean
) {
    BottomNavigationItem(
        icon = {
            Icon(
                painter = painterResource(item.provideIcon(isSelected)),
                contentDescription = stringResource(item.label)
            )
        },
        label = {
            Text(
                text = stringResource(id = item.label),
                style = CoinTheme.typography.subtitle5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        selectedContentColor = CoinTheme.color.primary,
        unselectedContentColor = CoinTheme.color.secondary,
        alwaysShowLabel = true,
        selected = isSelected,
        onClick = {
            navController.navigate(item.direction) {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
            }
        }
    )
}