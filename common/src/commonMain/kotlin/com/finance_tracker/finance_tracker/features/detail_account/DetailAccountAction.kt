package com.finance_tracker.finance_tracker.features.detail_account

sealed interface DetailAccountAction {
    object RefreshTransactions: DetailAccountAction
}