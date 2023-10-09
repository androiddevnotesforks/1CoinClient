package com.finance_tracker.finance_tracker.features.plans.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
fun handleAction(
    action: PlansOverviewViewAction,
    baseLocalsStorage: BaseLocalsStorage,
    pagerState: PagerState
) {
    when (action) {
        is PlansOverviewViewAction.ScrollToPage -> {
            baseLocalsStorage.coroutineScope.launch {
                pagerState.animateScrollToPage(action.page)
            }
        }
    }
}