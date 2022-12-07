package com.finance_tracker.finance_tracker.presentation.transactions

import androidx.paging.cachedIn
import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val transactionsInteractor: TransactionsInteractor
): KViewModel() {

    val paginatedTransactions = transactionsInteractor.getPaginatedTransactions()
        .cachedIn(viewModelScope)

    private val _events = MutableSharedFlow<TransactionEvents>(
        replay = 0, extraBufferCapacity = 1, BufferOverflow.DROP_LATEST
    )
    val events = _events.asSharedFlow()

    fun onDeleteTransactions(
        transactions: List<TransactionListModel.Data>
    ) {
        viewModelScope.launch {
            transactionsInteractor.deleteTransactions(
                transactions = transactions.map { it.transaction }
            )
            _events.tryEmit(TransactionEvents.RefreshTransactions)
        }
    }
}