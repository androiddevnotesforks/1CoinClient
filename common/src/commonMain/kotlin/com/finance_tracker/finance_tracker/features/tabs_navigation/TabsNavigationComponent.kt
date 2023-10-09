package com.finance_tracker.finance_tracker.features.tabs_navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.DialogCloseable
import com.finance_tracker.finance_tracker.core.common.asImageDescResource
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.inject
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.features.analytics.AnalyticsComponent
import com.finance_tracker.finance_tracker.features.home.HomeComponent
import com.finance_tracker.finance_tracker.features.plans.overview.PlansOverviewComponent
import com.finance_tracker.finance_tracker.features.plans.set_limit.SetLimitComponent
import com.finance_tracker.finance_tracker.features.tabs_navigation.analytics.TabsNavigationAnalytics
import com.finance_tracker.finance_tracker.features.transactions.TransactionsComponent
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.resources.desc.image.ImageDesc
import kotlinx.serialization.Serializable

class TabsNavigationComponent(
    componentContext: ComponentContext,
    private val openAddTransactionScreen: () -> Unit,
    private val openAccountDetailScreen: (Account) -> Unit,
    private val openAccountsScreen: () -> Unit,
    private val openAddAccountScreen: () -> Unit,
    private val openEditTransactionScreen: (Transaction) -> Unit,
    private val openSettingsScreen: () -> Unit,
    private val openPresetCurrencyScreen: () -> Unit,
    private val openSetupPlanScreen: (YearMonth, Plan?) -> Unit,
): BaseComponent<TabsNavigationViewModel>(componentContext) {

    val allTabs = listOf(Config.Home, Config.Transactions, null, Config.Plans, Config.Analytics)
    private val navigation = StackNavigation<Config>()
    private val analytics = inject<TabsNavigationAnalytics>()
    override val viewModel: TabsNavigationViewModel = injectViewModel()

    private val bottomDialogNavigation: SlotNavigation<BottomDialogConfig> = SlotNavigation()
    val bottomDialogSlot: Value<ChildSlot<*, BottomDialogChild>> = childSlot(
        source = bottomDialogNavigation,
        serializer = BottomDialogConfig.serializer(),
        handleBackButton = true,
    ) { config, childComponentContext ->
        when (config) {
            is BottomDialogConfig.SetLimitDialog -> {
                BottomDialogChild.SetLimitDialogChild(
                    SetLimitComponent(
                        componentContext = childComponentContext,
                        yearMonth = config.yearMonth,
                        dismissDialog = { bottomDialogNavigation.dismiss() }
                    )
                )
            }
        }
    }

    val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Home,
        childFactory = ::child,
        handleBackButton = true
    )

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                is Action.OpenPresetCurrencyScreen -> {
                    openPresetCurrencyScreen()
                }
                Action.DismissBottomDialog -> {
                    bottomDialogNavigation.dismiss()
                }
            }
        }
    }

    private fun child(config: Config, componentContext: ComponentContext): Child {
        return when (config) {
            Config.Home -> {
                Child.Home(createHomeComponent(componentContext))
            }
            Config.Transactions -> {
                Child.Transactions(
                    TransactionsComponent(
                        componentContext = componentContext,
                        openTransactionDetailScreen = openEditTransactionScreen,
                        openAddTransactionScreen = openAddTransactionScreen,
                    )
                )
            }
            Config.Plans -> {
                Child.Plans(createPlansOverviewComponent(componentContext))
            }
            Config.Analytics -> {
                Child.Analytics(AnalyticsComponent(componentContext))
            }
        }
    }

    private fun createHomeComponent(componentContext: ComponentContext): HomeComponent {
        return HomeComponent(
            componentContext = componentContext,
            openAccountDetailScreen = openAccountDetailScreen,
            openAccountsScreen = openAccountsScreen,
            openAddAccountScreen = openAddAccountScreen,
            openEditTransactionScreen = openEditTransactionScreen,
            openTransactionsScreen = {
                navigation.bringToFront(Config.Transactions)
            },
            openSettingsScreen = openSettingsScreen
        )
    }

    private fun createPlansOverviewComponent(
        componentContext: ComponentContext
    ): PlansOverviewComponent {
        return PlansOverviewComponent(
            componentContext = componentContext,
            openDialog = bottomDialogNavigation::activate,
            openSetupPlanScreen = openSetupPlanScreen
        )
    }

    fun onAddTransactionClick() {
        analytics.trackAddTransactionClick()
        openAddTransactionScreen()
    }

    fun onChangeTab(child: Config) {
        analytics.trackTabClick(getEventNameForTab(child))
        navigation.bringToFront(child)
    }

    private fun getEventNameForTab(child: Config): String {
        return when (child) {
            Config.Analytics -> "TabAnalytics"
            Config.Home -> "TabHome"
            Config.Plans -> "TabPlans"
            Config.Transactions -> "TabTransactions"
        }
    }

    sealed class Child {
        class Home(val component: HomeComponent) : Child()
        class Transactions(val component: TransactionsComponent) : Child()
        class Plans(val component: PlansOverviewComponent) : Child()
        class Analytics(val component: AnalyticsComponent) : Child()
    }

    @Serializable
    sealed class Config(
        val title: StringDesc,
        val selectedIcon: ImageDesc,
        val unselectedIcon: ImageDesc
    ) {

        @Serializable
        data object Home : Config(
            title = MR.strings.tab_home.desc(),
            selectedIcon = MR.images.ic_home_active.asImageDescResource(),
            unselectedIcon = MR.images.ic_home_inactive.asImageDescResource(),
        )

        @Serializable
        data object Transactions : Config(
            title = MR.strings.tab_transactions.desc(),
            selectedIcon = MR.images.ic_transactions_active.asImageDescResource(),
            unselectedIcon = MR.images.ic_transactions_inactive.asImageDescResource(),
        )

        @Serializable
        data object Plans : Config(
            title = MR.strings.tab_plans.desc(),
            selectedIcon = MR.images.ic_plans_active.asImageDescResource(),
            unselectedIcon = MR.images.ic_plans_inactive.asImageDescResource(),
        )

        @Serializable
        data object Analytics : Config(
            title = MR.strings.tab_analytics.desc(),
            selectedIcon = MR.images.ic_analytics_active.asImageDescResource(),
            unselectedIcon = MR.images.ic_analytics_inactive.asImageDescResource(),
        )
    }

    sealed interface BottomDialogChild: DialogCloseable {
        data class SetLimitDialogChild(
            val component: SetLimitComponent
        ): BottomDialogChild
    }

    @Serializable
    sealed interface BottomDialogConfig {
        @Serializable
        data class SetLimitDialog(
            val yearMonth: YearMonth
        ): BottomDialogConfig
    }

    sealed interface Action {
        data object OpenPresetCurrencyScreen: Action
        data object DismissBottomDialog: Action
    }
}