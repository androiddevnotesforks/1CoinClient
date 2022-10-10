package com.finance_tracker.finance_tracker.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.navigation.TabNavGraph
import com.finance_tracker.finance_tracker.core.ui.ItemWrapper
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.presentation.transactions.views.TransactionsAppBar
import com.finance_tracker.finance_tracker.theme.CoinTheme
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel

@TabNavGraph
@Destination
@Composable
fun TransactionsScreen(
    viewModel: TransactionsViewModel = getViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TransactionsAppBar()

        val transactions by viewModel.transactions.collectAsState()
        if (transactions.isEmpty()) {
            EmptyTransactionsStub()
        } else {
            TransactionsList(transactions = transactions)
        }
    }
}

@Composable
fun TransactionsList(
    modifier: Modifier = Modifier,
    transactions: List<TransactionUiModel>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        itemsIndexed(transactions) { index, transactionModel ->
            when (transactionModel) {
                is TransactionUiModel.Data -> {
                    TransactionItem(
                        transaction = transactionModel.transaction,
                        isFirstItem = index == 0,
                        isLastItem = index == transactions.lastIndex
                    )
                }
                is TransactionUiModel.DateAndDayTotal -> {
                    DayTotalHeader(
                        dayTotalModel = transactionModel
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyTransactionsStub(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = "", // TODO: Empty text
        textAlign = TextAlign.Center
    )
}

@Composable
private fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    isFirstItem: Boolean = true,
    isLastItem: Boolean = true
) {
    val category = transaction.category ?: Category.EMPTY
    ItemWrapper(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 1.dp),
        isFirstItem = isFirstItem,
        isLastItem = isLastItem
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 6.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = CoinTheme.color.secondaryBackground, shape = CircleShape)
                    .padding(12.dp),
                painter = painterResource(category.icon),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = category.name,
                    style = CoinTheme.typography.body2
                )
                Text(
                    text = transaction.account.name,
                    style = CoinTheme.typography.subtitle2,
                    color = LocalContentColor.current.copy(alpha = 0.5f)
                )
            }

            Text(
                text = transaction.amountCurrency + transaction.amount,
                style = CoinTheme.typography.body2
            )
        }
    }
}

@Composable
private fun DayTotalHeader(
    modifier: Modifier = Modifier,
    dayTotalModel: TransactionUiModel.DateAndDayTotal
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = dayTotalModel.date.toString(),
            style = CoinTheme.typography.subtitle2,
            color = CoinTheme.color.secondary
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "2",
            style = CoinTheme.typography.subtitle2,
            color = CoinTheme.color.primary
        )
        Text(
            text = "3",
            style = CoinTheme.typography.subtitle2,
            color = CoinTheme.color.content
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionsScreenPreview() {
    TransactionsScreen()
}