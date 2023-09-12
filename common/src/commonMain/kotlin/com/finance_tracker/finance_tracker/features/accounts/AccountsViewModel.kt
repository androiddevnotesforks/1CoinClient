package com.finance_tracker.finance_tracker.features.accounts

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.AccountsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.features.accounts.analytics.AccountsAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountsViewModel(
    private val accountsInteractor: AccountsInteractor,
    private val accountsAnalytics: AccountsAnalytics
): BaseViewModel<AccountsAction>() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    init {
        accountsAnalytics.trackScreenOpen()
    }

    fun onScreenComposed() {
        loadAccounts()
    }

    fun onAccountClick(account: Account) {
        accountsAnalytics.trackAccountClick(account)
        viewAction = AccountsAction.OpenEditAccountScreen(account)
    }

    fun onBackClick() {
        accountsAnalytics.trackAccountsScreenBackClick()
        viewAction = AccountsAction.CloseScreen
    }

    fun onAddAccountClick() {
        accountsAnalytics.trackAddAccountClick()
        viewAction = AccountsAction.OpenAddAccountScreen
    }

    fun onCardMove(fromIndex: Int, toIndex: Int) {
        val firstAccout = _accounts.value[fromIndex]
        val secondAccout = _accounts.value[toIndex]

        viewModelScope.launch {
            val newList = _accounts.value.toMutableList()
            newList[fromIndex] = secondAccout
            newList[toIndex] = firstAccout
            _accounts.value = newList

            accountsInteractor.updateAccountPosition(position = fromIndex, accountId = secondAccout.id)
            accountsInteractor.updateAccountPosition(position = toIndex, accountId = firstAccout.id)
        }
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _accounts.value = accountsInteractor.getAllAccountsFromDatabase()
        }
    }
}
