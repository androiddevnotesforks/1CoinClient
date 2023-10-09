package com.finance_tracker.finance_tracker.features.plans.overview

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.finance_tracker.finance_tracker.features.tabs_navigation.TabsNavigationComponent

class PlansOverviewComponent(
    componentContext: ComponentContext,
    private val openDialog: (TabsNavigationComponent.BottomDialogConfig) -> Unit,
    private val openSetupPlanScreen: (YearMonth, Plan?) -> Unit
): BaseComponent<PlansOverviewViewModel>(componentContext) {

    override val viewModel: PlansOverviewViewModel = injectViewModel()

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                is Action.ShowSetLimitDialog -> {
                    openDialog(
                        TabsNavigationComponent.BottomDialogConfig.SetLimitDialog(
                            yearMonth = action.yearMonth
                        )
                    )
                }

                is Action.OpenEditPlanScreen -> {
                    openSetupPlanScreen(action.yearMonth, action.plan)
                }

                is Action.OpenSetupPlanScreen -> {
                    openSetupPlanScreen(action.yearMonth, null)
                }
            }
        }
    }

    sealed interface Action {
        data class ShowSetLimitDialog(
            val yearMonth: YearMonth
        ) : Action

        data class OpenSetupPlanScreen(
            val yearMonth: YearMonth
        ): Action

        data class OpenEditPlanScreen(
            val yearMonth: YearMonth,
            val plan: Plan
        ): Action
    }
}