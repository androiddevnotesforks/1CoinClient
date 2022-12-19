package com.finance_tracker.finance_tracker.presentation.detail_account

import androidx.paging.cachedIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DetailAccountViewModel(
    account: Account,
    transactionsInteractor: TransactionsInteractor,
    accountsRepository: AccountsRepository
): BaseViewModel<Nothing>() {

    val paginatedTransactions = transactionsInteractor.getPaginatedTransactionsByAccountId(account.id)
        .cachedIn(viewModelScope)

    val accountData = accountsRepository.getAccountByIdFlow(account.id)
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = account)
}