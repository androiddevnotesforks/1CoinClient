package com.finance_tracker.finance_tracker.features.plans.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import com.finance_tracker.finance_tracker.features.plans.setup.SetupPlanScreenParams
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.extensions.push

@OptIn(ExperimentalFoundationApi::class)
fun handleAction(
    action: PlansOverviewAction,
    baseLocalsStorage: BaseLocalsStorage,
    pagerState: PagerState
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        is PlansOverviewAction.OpenSetupPlanScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.SetupPlan.name,
                params = SetupPlanScreenParams(
                    yearMonth = action.yearMonth
                )
            )
        }
        is PlansOverviewAction.OpenEditPlanScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.SetupPlan.name,
                params = SetupPlanScreenParams(
                    plan = action.plan,
                    yearMonth = action.yearMonth
                )
            )
        }
        is PlansOverviewAction.ScrollToPage -> {
            baseLocalsStorage.coroutineScope.launch {
                pagerState.animateScrollToPage(action.page)
            }
        }
    }
}