package com.finance_tracker.finance_tracker.presentation.transactions

import androidx.lifecycle.ViewModel
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class TransactionsViewModel(
    private val transactionsRepository: TransactionsRepository
): ViewModel() {

    private val _transactions: MutableStateFlow<List<Transaction>> = MutableStateFlow(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        _transactions.value = transactionsRepository.getAllTransactions()
    }
}