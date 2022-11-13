package com.finance_tracker.finance_tracker.presentation.transactions

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel constructor(
    private val transactionsInteractor: TransactionsInteractor,
    private val accountsRepository: AccountsRepository
): KViewModel() {

    private val _transactions: MutableStateFlow<List<TransactionListModel>> = MutableStateFlow(emptyList())
    val transactions: StateFlow<List<TransactionListModel>> = _transactions.asStateFlow()

    private var loadTransactionsJob: Job? = null

    fun loadTransactions() {
        loadTransactionsJob?.cancel()
        loadTransactionsJob = viewModelScope.launch {
            _transactions.update { transactionsInteractor.getTransactions() }
        }
    }

    fun onDeleteTransactions(
        transactions: List<TransactionListModel.Data>
    ) {
        viewModelScope.launch {
            transactionsInteractor.deleteTransactions(
                transactions = transactions.map { it.transaction }
            )

            val transactionsIterator = transactions.iterator()

            transactionsIterator.forEach {
                if(it.transaction.type == TransactionType.Expense) {
                    accountsRepository.increaseAccountBalance(it.transaction.account.id, it.transaction.amount)
                } else {
                    accountsRepository.reduceAccountBalance(it.transaction.account.id, it.transaction.amount)
                }
            }
            loadTransactions()
        }
    }
}