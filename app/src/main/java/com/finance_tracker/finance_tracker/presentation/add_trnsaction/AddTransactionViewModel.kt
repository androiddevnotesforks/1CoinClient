package com.finance_tracker.finance_tracker.presentation.add_trnsaction

import androidx.lifecycle.ViewModel
import com.finance_tracker.finance_tracker.data.database.mappers.toDomainModel
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.financetracker.financetracker.AccountsEntityQueries
import com.financetracker.financetracker.TransactionsEntityQueries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddTransactionViewModel(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    private val accountsEntityQueries: AccountsEntityQueries,
): ViewModel() {

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        _accounts.value = accountsEntityQueries.getAllAccounts().executeAsList()
            .map { it.toDomainModel() }
    }

    fun addTransaction(transaction: Transaction) {
        transactionsEntityQueries.insertTransaction(
            id = null,
            type = transaction.type,
            amount = transaction.amount,
            amountCurrency = transaction.amountCurrency,
            category = transaction.category,
            accountId = transaction.account.id,
            date = transaction.date?.time ?: 0
        )
    }
}