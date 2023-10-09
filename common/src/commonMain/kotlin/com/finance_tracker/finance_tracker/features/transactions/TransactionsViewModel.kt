package com.finance_tracker.finance_tracker.features.transactions

import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.finance_tracker.finance_tracker.core.common.view_models.ComponentViewModel
import com.finance_tracker.finance_tracker.domain.interactors.transactions.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.features.transactions.analytics.TransactionsAnalytics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val transactionsInteractor: TransactionsInteractor,
    private val transactionsAnalytics: TransactionsAnalytics
): ComponentViewModel<TransactionsAction, TransactionsComponent.Action>() {

    private val _selectedItems = MutableStateFlow(emptyList<TransactionListModel.Data>())
    val selectedItems = _selectedItems.asStateFlow()

    init {
        transactionsAnalytics.trackScreenOpen()
        observeTransactionsSizeUpdates()
    }

    fun getPaginatedTransactions(): Flow<PagingData<TransactionListModel>> {
        return transactionsInteractor.getPaginatedTransactions()
            .cachedIn(viewModelScope)
    }

    fun onConfirmDeleteTransactionsClick() {
        transactionsAnalytics.trackConfirmDeleteTransactionsClick()
        viewModelScope.launch {
            transactionsInteractor.deleteTransactions(
                transactions = selectedItems.value.map { it.transaction }
            )
            viewAction = TransactionsAction.RefreshTransactions
        }
        componentAction = TransactionsComponent.Action.DismissBottomDialog
        unselectAllItems()
    }

    private fun observeTransactionsSizeUpdates() {
        transactionsInteractor.getTransactionsSizeUpdates()
            .onEach { viewAction = TransactionsAction.RefreshTransactions }
            .launchIn(viewModelScope)
    }

    fun onCancelDeletingTransactionsClick() {
        transactionsAnalytics.trackCancelDeletingTransactionsClick()
        componentAction = TransactionsComponent.Action.DismissBottomDialog
    }

    fun onTransactionClick(transaction: Transaction) {
        transactionsAnalytics.trackTransactionClick(transaction)
        componentAction = TransactionsComponent.Action.OpenTransactionDetailScreen(transaction)
    }

    fun onCloseAppBarMenuClick() {
        transactionsAnalytics.trackCloseAppBarMenuClick()
        unselectAllItems()
    }

    fun onDeleteTransactionsClick(selectedItemsCount: Int) {
        transactionsAnalytics.trackDeleteTransactionsClick()
        componentAction = TransactionsComponent.Action.ShowDeleteDialog(selectedItemsCount)
    }

    fun onAddTransactionClick() {
        transactionsAnalytics.onAddTransactionClick()
        componentAction = TransactionsComponent.Action.OpenAddTransactionScreen
    }

    fun onSelectItem(item: TransactionListModel.Data) {
        if (item.isSelected.value) {
            onRemoveSelectedItem(item)
        } else {
            onAddSelectedItem(item)
        }
    }

    private fun onAddSelectedItem(item: TransactionListModel.Data) {
        item.isSelected.value = true
        _selectedItems.update { selectedItems ->
            selectedItems + item
        }
    }

    private fun onRemoveSelectedItem(item: TransactionListModel.Data) {
        item.isSelected.value = false
        _selectedItems.update { selectedItems ->
            selectedItems - item
        }
    }

    private fun unselectAllItems() {
        selectedItems.value.forEach { it.isSelected.value = false }
        _selectedItems.update { emptyList() }
    }

    fun onDismissDialog() {
        componentAction = TransactionsComponent.Action.DismissBottomDialog
    }
}