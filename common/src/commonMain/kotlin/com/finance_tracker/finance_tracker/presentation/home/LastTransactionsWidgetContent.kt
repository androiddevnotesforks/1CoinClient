package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.ui.transactions.EmptyTransactionsStub
import com.finance_tracker.finance_tracker.core.ui.transactions.TransactionItem
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun LastTransactionsWidgetContent(
    lastTransactions: List<TransactionListModel.Data>,
    modifier: Modifier = Modifier
) {

    val navController = LocalRootController.current.findRootController()

    if (lastTransactions.isEmpty()) {
        EmptyTransactionsStub(
            modifier = modifier,
            hasBorder = false
        )
    } else {
        LastTransactionsColumn(
            modifier = modifier,
            transactions = lastTransactions,
            navController = navController,
        )
    }
}

@Composable
private fun LastTransactionsColumn(
    transactions: List<TransactionListModel.Data>,
    navController: RootController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                vertical = 10.dp
            )
    ) {
        transactions.forEach { transactionData ->
            TransactionItem(
                transactionData = transactionData,
                onClick = {
                    navController.push(
                        screen = MainNavigationTree.AddTransaction.name,
                        params = transactionData.transaction
                    )
                }
            )
        }
    }
}
