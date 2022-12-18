package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.lazy.LazyListState
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import kotlinx.coroutines.launch

sealed interface HomeAction {
    data class ScrollToItemAccounts(val index: Int): HomeAction
}

fun handleAction(
    action: HomeAction,
    baseLocalsStorage: BaseLocalsStorage,
    accountsLazyListState: LazyListState
) {
    when (action) {
        is HomeAction.ScrollToItemAccounts -> {
            baseLocalsStorage.coroutineScope.launch {
                accountsLazyListState.animateScrollToItem(action.index)
            }
        }
    }
}