package com.finance_tracker.finance_tracker.features.home

sealed interface HomeAction {
    data class ScrollToItemAccounts(val index: Int): HomeAction
}