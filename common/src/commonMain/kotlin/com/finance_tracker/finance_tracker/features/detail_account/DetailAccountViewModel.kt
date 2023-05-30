package com.finance_tracker.finance_tracker.features.detail_account

import app.cash.paging.cachedIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.interactors.transactions.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.features.detail_account.analytics.DetailAccountAnalytics
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

@Suppress("ConstructorParameterNaming")
class DetailAccountViewModel(
    _account: Account,
    private val transactionsInteractor: TransactionsInteractor,
    accountsRepository: AccountsRepository,
    private val detailAccountAnalytics: DetailAccountAnalytics
): BaseViewModel<DetailAccountAction>() {

    val paginatedTransactions = transactionsInteractor.getPaginatedTransactionsByAccountId(_account.id)
        .cachedIn(viewModelScope)

    val accountData = accountsRepository.getAccountByIdFlow(_account.id)
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = _account)

    init {
        detailAccountAnalytics.trackScreenOpen()
        observeTransactionsSizeUpdates()
    }

    private fun observeTransactionsSizeUpdates() {
        transactionsInteractor.getTransactionsByAccountIdSizeUpdates(
            id = accountData.value.id
        )
            .onEach { viewAction = DetailAccountAction.RefreshTransactions }
            .launchIn(viewModelScope)
    }

    fun onBackClick() {
        detailAccountAnalytics.trackBackClick()
        viewAction = DetailAccountAction.CloseScreen
    }

    fun onEditClick() {
        detailAccountAnalytics.trackEditAccountClick(accountData.value)
        viewAction = DetailAccountAction.OpenEditAccountScreen(accountData.value)
    }

    fun onTransactionClick(transaction: Transaction) {
        detailAccountAnalytics.trackTransactionClick(transaction)
        viewAction = DetailAccountAction.OpenEditTransactionScreen(transaction)
    }

    fun onIconClick() {
        detailAccountAnalytics.trackIconClick(accountData.value)
        viewAction = DetailAccountAction.OpenEditAccountScreen(accountData.value)
    }

    fun onAddTransactionClick() {
        detailAccountAnalytics.trackAddTransactionClick(accountData.value)
        viewAction = DetailAccountAction.OpenAddTransactionScreen(accountData.value)
    }
}