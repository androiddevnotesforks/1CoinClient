package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
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

    Box(modifier = modifier
        .fillMaxWidth()
        .padding(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp,
        )
        .border(
            width = 1.dp,
            color = CoinTheme.color.dividers,
            shape = RoundedCornerShape(12.dp)
        )
    ) {
        if (lastTransactions.isEmpty()) {
            EmptyTransactionsStub(hasBorder = false)
        } else {
            LastTransactionsColumn(
                transactions = lastTransactions,
                navController = navController,
            )
        }
    }
}

@Composable
private fun LastTransactionsColumn(
    transactions: List<TransactionListModel.Data>,
    navController: RootController,
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                vertical = 10.dp
            )
    ) {
        items(transactions, key = { it.id }) { transactionData ->
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
