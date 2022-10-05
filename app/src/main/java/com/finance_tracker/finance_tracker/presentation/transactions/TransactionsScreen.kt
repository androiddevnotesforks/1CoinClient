package com.finance_tracker.finance_tracker.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.core.navigation.TabNavGraph
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
    transactions: List<Transaction>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        items(transactions) { transaction ->
            TransactionItem(
                transaction = transaction
            )
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color.Red.copy(0.2f))
            .padding(vertical = 6.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(44.dp)
                .background(color = CoinTheme.color.secondaryBackground, shape = CircleShape)
                .padding(12.dp),
            painter = painterResource(R.drawable.ic_plus),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = transaction.category.orEmpty(),
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

@Preview(showBackground = true)
@Composable
fun TransactionsScreenPreview() {
    TransactionsScreen()
}