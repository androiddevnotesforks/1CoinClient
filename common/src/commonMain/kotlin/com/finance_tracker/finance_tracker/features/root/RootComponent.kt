package com.finance_tracker.finance_tracker.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.globalKoin
import com.finance_tracker.finance_tracker.core.feature_flags.FeatureFlag
import com.finance_tracker.finance_tracker.core.feature_flags.FeaturesManager
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.features.accounts.AccountsComponent
import com.finance_tracker.finance_tracker.features.add_account.AddAccountComponent
import com.finance_tracker.finance_tracker.features.add_category.AddCategoryComponent
import com.finance_tracker.finance_tracker.features.add_transaction.AddTransactionComponent
import com.finance_tracker.finance_tracker.features.add_transaction.AddTransactionScreenParams
import com.finance_tracker.finance_tracker.features.category_settings.CategorySettingsComponent
import com.finance_tracker.finance_tracker.features.dashboard_settings.DashboardSettingsComponent
import com.finance_tracker.finance_tracker.features.detail_account.DetailAccountComponent
import com.finance_tracker.finance_tracker.features.plans.setup.SetupPlanComponent
import com.finance_tracker.finance_tracker.features.plans.setup.SetupPlanScreenParams
import com.finance_tracker.finance_tracker.features.preset_currency.PresetCurrencyComponent
import com.finance_tracker.finance_tracker.features.registration.RegistrationComponent
import com.finance_tracker.finance_tracker.features.select_currency.SelectCurrencyComponent
import com.finance_tracker.finance_tracker.features.settings.SettingsComponent
import com.finance_tracker.finance_tracker.features.tabs_navigation.TabsNavigationComponent
import com.finance_tracker.finance_tracker.presentation.models.AccountSerializable
import com.finance_tracker.finance_tracker.presentation.models.CategorySerializable
import com.finance_tracker.finance_tracker.presentation.models.PlanSerializable
import com.finance_tracker.finance_tracker.presentation.models.TransactionSerializable
import com.finance_tracker.finance_tracker.presentation.models.toDomain
import com.finance_tracker.finance_tracker.presentation.models.toSerializable
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext {

    private val featuresManager: FeaturesManager = globalKoin.get()
    private val currenciesInteractor: CurrenciesInteractor = globalKoin.get()

    private val navigation = StackNavigation<Config>()
    val childStack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = if (
                featuresManager.isEnabled(FeatureFlag.Authorization) &&
                !currenciesInteractor.isPrimaryCurrencySelectedSync()
            ) {
                Config.Registration
            } else {
                Config.TabsNavigation
            },
            handleBackButton = true,
            childFactory = ::createChild,
        )

    // TODO: Navigation Split screens by subflow - https://finance-tracker-app.atlassian.net/browse/FT-329
    @Suppress("CyclomaticComplexMethod")
    private fun createChild(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.AddAccount -> {
                Child.AddAccountChild(
                    addAccount(
                        componentContext = componentContext,
                        account = config.account?.toDomain()
                    )
                )
            }
            is Config.Accounts -> {
                Child.AccountsChild(accounts(componentContext))
            }
            is Config.TabsNavigation -> {
                Child.TabsNavigationChild(tabsNavigation(componentContext))
            }
            is Config.AddTransaction -> {
                Child.AddTransactionChild(
                    addTransaction(
                        preselectedAccount = config.account?.toDomain(),
                        transaction = config.transaction?.toDomain(),
                        componentContext = componentContext
                    )
                )
            }
            is Config.Settings -> {
                Child.SettingsChild(
                    SettingsComponent(
                        componentContext = componentContext,
                        openCategorySettingsScreen = {
                            navigation.push(Config.CategorySettings)
                        },
                        openDashboardSettingsScreen = {
                            navigation.push(Config.DashboardSettings)
                        },
                        openSelectMainCurrencyScreen = {
                            navigation.push(Config.SelectMainCurrency)
                        },
                        back = {
                            navigation.pop()
                        }
                    )
                )
            }

            Config.CategorySettings -> {
                Child.CategorySettingsChild(
                    CategorySettingsComponent(
                        componentContext = componentContext,
                        close = {
                            navigation.pop()
                        },
                        openAddCategoryScreen = {
                            navigation.push(
                                Config.AddCategory(
                                    transactionType = it.toTransactionType().toSerializable()
                                )
                            )
                        },
                        openEditCategoryScreen = { transactionType, category ->
                            navigation.push(
                                Config.AddCategory(
                                    transactionType = transactionType.toSerializable(),
                                    category = category.toSerializable()
                                )
                            )
                        }
                    )
                )
            }

            Config.DashboardSettings -> {
                Child.DashboardSettingsChild(
                    DashboardSettingsComponent(
                        componentContext = componentContext,
                        back = {
                            navigation.pop()
                        }
                    )
                )
            }

            Config.SelectMainCurrency -> {
                Child.SelectMainCurrencyChild(
                    SelectCurrencyComponent(
                        componentContext = componentContext,
                        back = {
                            navigation.pop()
                        }
                    )
                )
            }

            Config.Registration -> {
                Child.RegistrationChild(
                    RegistrationComponent(
                        componentContext = componentContext,
                        openMainScreen = {
                            if (currenciesInteractor.isPrimaryCurrencySelectedSync()) {
                                navigation.replaceAll(Config.TabsNavigation)
                            } else {
                                navigation.replaceAll(Config.PresetCurrency)
                            }
                        }
                    )
                )
            }

            is Config.AccountDetail -> {
                Child.DetailAccountChild(
                    DetailAccountComponent(
                        account = config.account.toDomain(),
                        componentContext = componentContext,
                        close = {
                            navigation.pop()
                        },
                        openEditAccountScreen = { account ->
                            navigation.push(
                                Config.AddAccount(
                                    account = account.toSerializable()
                                )
                            )
                        },
                        openEditTransactionScreen = {
                            navigation.push(
                                Config.AddTransaction(
                                    transaction = it.toSerializable()
                                )
                            )
                        },
                        openAddTransactionScreen = { account ->
                            navigation.push(
                                Config.AddTransaction(
                                    account = account.toSerializable()
                                )
                            )
                        }
                    )
                )
            }

            is Config.AddCategory -> {
                Child.AddCategoryChild(
                    AddCategoryComponent(
                        componentContext = componentContext,
                        transactionType = config.transactionType.toDomain(),
                        category = config.category?.toDomain(),
                        close = {
                            navigation.pop()
                        }
                    )
                )
            }

            Config.PresetCurrency -> {
                Child.PresetCurrencyChild(
                    PresetCurrencyComponent(
                        componentContext = componentContext,
                        openMainScreen = {
                            navigation.replaceAll(Config.TabsNavigation)
                        }
                    )
                )
            }

            is Config.SetupPlan -> {
                Child.SetupPlanChild(
                    SetupPlanComponent(
                        componentContext = componentContext,
                        params = SetupPlanScreenParams(
                            yearMonth = config.yearMonth,
                            plan = config.plan
                        ),
                        close = {
                            navigation.pop()
                        }
                    )
                )
            }
        }

    sealed class Child {
        class AddAccountChild(val component: AddAccountComponent) : Child()
        class AccountsChild(val component: AccountsComponent) : Child()
        class TabsNavigationChild(val component: TabsNavigationComponent) : Child()
        class AddTransactionChild(val component: AddTransactionComponent) : Child()
        class SettingsChild(val component: SettingsComponent) : Child()
        class CategorySettingsChild(val component: CategorySettingsComponent) : Child()
        class DashboardSettingsChild(val component: DashboardSettingsComponent) : Child()
        class SelectMainCurrencyChild(val component: SelectCurrencyComponent) : Child()
        class RegistrationChild(val component: RegistrationComponent) : Child()
        class DetailAccountChild(val component: DetailAccountComponent) : Child()
        class AddCategoryChild(val component: AddCategoryComponent) : Child()
        class PresetCurrencyChild(val component: PresetCurrencyComponent) : Child()
        class SetupPlanChild(val component: SetupPlanComponent): Child()
    }

    private fun addAccount(
        componentContext: ComponentContext,
        account: Account? = null
    ): AddAccountComponent {
        return AddAccountComponent(
            componentContext = componentContext,
            account = account ?: Account.EMPTY,
            close = { navigation.pop() }
        )
    }

    private fun accounts(componentContext: ComponentContext): AccountsComponent {
        return AccountsComponent(
            componentContext = componentContext,
            close = {
                navigation.pop()
            },
            openAddAccountScreen = {
                navigation.push(
                    Config.AddAccount(account = null)
                )
            },
            openEditAccountScreen = { account ->
                navigation.push(
                    Config.AddAccount(account = account.toSerializable())
                )
            }
        )
    }

    private fun tabsNavigation(componentContext: ComponentContext): TabsNavigationComponent {
        return TabsNavigationComponent(
            componentContext = componentContext,
            openAddTransactionScreen = {
                navigation.push(
                    Config.AddTransaction()
                )
            },
            openAccountDetailScreen = { account ->
                navigation.push(
                    Config.AccountDetail(account = account.toSerializable())
                )
            },
            openAccountsScreen = { navigation.push(Config.Accounts) },
            openAddAccountScreen = {
                navigation.push(Config.AddAccount(account = null))
            },
            openEditTransactionScreen = { transaction ->
                navigation.push(
                    Config.AddTransaction(
                        transaction = transaction.toSerializable()
                    )
                )
            },
            openSettingsScreen = {
                navigation.push(Config.Settings)
            },
            openPresetCurrencyScreen = {
                navigation.replaceAll(Config.PresetCurrency)
            },
            openSetupPlanScreen = { yearMonth, plan ->
                navigation.push(
                    Config.SetupPlan(
                        yearMonth = yearMonth,
                        plan = plan?.toSerializable()
                    )
                )
            }
        )
    }

    private fun addTransaction(
        preselectedAccount: Account? = null,
        transaction: Transaction? = null,
        componentContext: ComponentContext
    ): AddTransactionComponent {
        return AddTransactionComponent(
            componentContext = componentContext,
            params = AddTransactionScreenParams(
                preselectedAccount = preselectedAccount,
                transaction = transaction
            ),
            popBackStack = { navigation.pop() },
            openAddAccountScreen = {
                navigation.push(Config.AddAccount(account = null))
            },
            openAddCategoryScreen = { transactionType ->
                navigation.push(
                    Config.AddCategory(transactionType = transactionType.toSerializable())
                )
            }
        )
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data class AddAccount(
            val account: AccountSerializable?
        ) : Config

        @Serializable
        data object Accounts : Config

        @Serializable
        data object TabsNavigation : Config

        @Serializable
        data class AddTransaction(
            val account: AccountSerializable? = null,
            val transaction: TransactionSerializable? = null
        ) : Config

        @Serializable
        data object Settings : Config

        @Serializable
        data object CategorySettings : Config

        @Serializable
        data object DashboardSettings : Config

        @Serializable
        data object SelectMainCurrency : Config

        @Serializable
        data object Registration : Config

        @Serializable
        data class AccountDetail(
            val account: AccountSerializable
        ) : Config

        @Serializable
        data class AddCategory(
            val transactionType: TransactionSerializable.TypeSerializable,
            val category: CategorySerializable? = null
        ) : Config

        @Serializable
        data object PresetCurrency : Config

        @Serializable
        data class SetupPlan(
            val yearMonth: YearMonth,
            val plan: PlanSerializable?
        ) : Config
    }
}