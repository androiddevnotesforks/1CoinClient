package com.finance_tracker.finance_tracker.features.plans.overview

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push

fun handleAction(
    action: PlansOverviewAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        PlansOverviewAction.OpenSetupPlanScreen -> {
            val navController = rootController.findRootController()
            navController.push(screen = MainNavigationTree.SetupPlan.name)
        }
    }
}