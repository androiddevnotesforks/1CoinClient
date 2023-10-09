package com.finance_tracker.finance_tracker.features.tabs_navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.NoRippleTheme
import com.finance_tracker.finance_tracker.core.ui.BottomNavigationBar
import com.finance_tracker.finance_tracker.core.ui.decompose_ext.subscribeBottomDialog
import com.finance_tracker.finance_tracker.features.analytics.AnalyticsScreen
import com.finance_tracker.finance_tracker.features.home.HomeScreen
import com.finance_tracker.finance_tracker.features.plans.overview.PlansOverviewScreen
import com.finance_tracker.finance_tracker.features.plans.overview.views.set_limit.SetLimitDialog
import com.finance_tracker.finance_tracker.features.transactions.TransactionsScreen
import dev.icerock.moko.resources.compose.painterResource

@Composable
internal fun TabsNavigationScreen(
    component: TabsNavigationComponent
) {
    val viewModel = component.viewModel
    val stack by component.stack.subscribeAsState()
    val activeChild = stack.active.instance

    Box {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    allTabs = component.allTabs,
                    activeChild = activeChild,
                    onItemSelect = { tab ->
                        component.onChangeTab(tab)
                    }
                )
            },
            floatingActionButton = {
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    FloatingActionButton(
                        modifier = Modifier
                            .scaleClickAnimation(),
                        backgroundColor = CoinTheme.color.primary,
                        contentColor = CoinTheme.color.primaryVariant,
                        onClick = { component.onAddTransactionClick() }
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(MR.images.ic_plus),
                            contentDescription = null,
                            tint = CoinTheme.color.white
                        )
                    }
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center
        ) { _ ->
            Children(
                stack = stack,
                animation = stackAnimation(fade())
            ) {
                when (val child = it.instance) {
                    is TabsNavigationComponent.Child.Analytics -> {
                        AnalyticsScreen(child.component)
                    }

                    is TabsNavigationComponent.Child.Home -> {
                        HomeScreen(child.component)
                    }

                    is TabsNavigationComponent.Child.Plans -> {
                        PlansOverviewScreen(child.component)
                    }

                    is TabsNavigationComponent.Child.Transactions -> {
                        TransactionsScreen(child.component)
                    }
                }
            }
        }

        component.bottomDialogSlot.subscribeBottomDialog(
            onDismissRequest = viewModel::onDismissBottomDialog
        ) { child ->
            when (child) {
                is TabsNavigationComponent.BottomDialogChild.SetLimitDialogChild -> {
                    SetLimitDialog(component = child.component)
                }
            }
        }
    }
}