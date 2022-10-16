package com.finance_tracker.finance_tracker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeScreenViewModel(
    private val repository: AccountsRepository
): ViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _accounts.value  = repository.getAllAccountsFromDatabase()
        }
    }
}