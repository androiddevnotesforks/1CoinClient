package com.finance_tracker.finance_tracker.features.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.finance_tracker.finance_tracker.features.accounts.AccountsScreen
import com.finance_tracker.finance_tracker.features.add_account.AddAccountScreen
import com.finance_tracker.finance_tracker.features.add_category.AddCategoryScreen
import com.finance_tracker.finance_tracker.features.add_transaction.AddTransactionScreen
import com.finance_tracker.finance_tracker.features.category_settings.CategorySettingsScreen
import com.finance_tracker.finance_tracker.features.dashboard_settings.DashboardSettingsScreen
import com.finance_tracker.finance_tracker.features.detail_account.DetailAccountScreen
import com.finance_tracker.finance_tracker.features.plans.setup.SetupPlanScreen
import com.finance_tracker.finance_tracker.features.preset_currency.PresetCurrencyScreen
import com.finance_tracker.finance_tracker.features.registration.RegistrationScreen
import com.finance_tracker.finance_tracker.features.select_currency.SelectCurrencyScreen
import com.finance_tracker.finance_tracker.features.settings.SettingsScreen
import com.finance_tracker.finance_tracker.features.tabs_navigation.TabsNavigationScreen

@Composable
fun RootScreen(
    component: RootComponent
) {
    val childStack by component.childStack.subscribeAsState()
    
    Children(
        stack = childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.AddAccountChild -> {
                AddAccountScreen(
                    component = child.component
                )
            }

            is RootComponent.Child.AccountsChild -> {
                AccountsScreen(
                    component = child.component
                )
            }

            is RootComponent.Child.TabsNavigationChild -> {
                TabsNavigationScreen(
                    component = child.component
                )
            }
            is RootComponent.Child.AddTransactionChild -> {
                AddTransactionScreen(
                    component = child.component
                )
            }
            is RootComponent.Child.SettingsChild -> {
                SettingsScreen(
                    component = child.component
                )
            }

            is RootComponent.Child.CategorySettingsChild -> {
                CategorySettingsScreen(
                    component = child.component
                )
            }

            is RootComponent.Child.DashboardSettingsChild -> {
                DashboardSettingsScreen(
                    component = child.component
                )
            }

            is RootComponent.Child.SelectMainCurrencyChild -> {
                SelectCurrencyScreen(
                    component = child.component
                )
            }

            is RootComponent.Child.RegistrationChild -> {
                RegistrationScreen(
                    component = child.component
                )
            }

            is RootComponent.Child.DetailAccountChild -> {
                DetailAccountScreen(
                    component = child.component
                )
            }

            is RootComponent.Child.AddCategoryChild -> {
                AddCategoryScreen(
                    component = child.component
                )
            }

            is RootComponent.Child.PresetCurrencyChild -> {
                PresetCurrencyScreen(
                    component = child.component
                )
            }

            is RootComponent.Child.SetupPlanChild -> {
                SetupPlanScreen(
                    component = child.component
                )
            }
        }
    }
}