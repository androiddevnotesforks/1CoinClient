package com.finance_tracker.finance_tracker.core.ui.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.DateFormatType
import com.finance_tracker.finance_tracker.core.common.pagination.LazyPagingItems
import com.finance_tracker.finance_tracker.core.common.pagination.items
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

private val ShortDateFormatter = SimpleDateFormat("dd.MM")
private val FullDateFormatter = SimpleDateFormat("dd.MM.yyyy")

@Composable
fun CommonTransactionsList(
    transactions: LazyPagingItems<TransactionListModel>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (TransactionListModel.Data) -> Unit = {},
    onLongClick: (TransactionListModel.Data) -> Unit = {}
) {
    if (transactions.itemCount == 0) {
        EmptyStub(
            modifier = modifier,
            image = rememberVectorPainter("transactions_empty"),
            text = stringResource("add_transaction"),
        )
    } else {
        TransactionsList(
            modifier = modifier,
            transactions = transactions,
            contentPadding = contentPadding,
            onClick = onClick,
            onLongClick = onLongClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionsList(
    transactions: LazyPagingItems<TransactionListModel>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (TransactionListModel.Data) -> Unit = {},
    onLongClick: (TransactionListModel.Data) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(transactions, key = { it.id }) { transactionModel ->
            if (transactionModel == null) return@items

            when (transactionModel) {
                is TransactionListModel.Data -> {
                    TransactionItem(
                        modifier = Modifier.animateItemPlacement(),
                        transactionData = transactionModel,
                        onClick = { onClick.invoke(transactionModel) },
                        onLongClick = { onLongClick.invoke(transactionModel) }
                    )
                }
                is TransactionListModel.DateAndDayTotal -> {
                    DayTotalHeader(dayTotalModel = transactionModel)
                }
            }
        }
    }
}

@Composable
private fun DayTotalHeader(
    dayTotalModel: TransactionListModel.DateAndDayTotal,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        val formattedDate = when {
            dayTotalModel.date.isToday() -> {
                stringResource("transactions_today")
            }
            dayTotalModel.date.isYesterday() -> {
                stringResource("transactions_yesterday")
            }
            dayTotalModel.date.isCurrentYear() -> {
                ShortDateFormatter.format(dayTotalModel.date)
            }
            else -> {
                FullDateFormatter.format(dayTotalModel.date)
            }
        }
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = formattedDate,
            style = CoinTheme.typography.subtitle2,
            color = CoinTheme.color.secondary
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .defaultMinSize(minWidth = 16.dp)
        )
    }
}

private fun Date.isToday(): Boolean {
    val nowDateString = DateFormatType.CommonDateFormat.format(Date())
    val currentDateString = DateFormatType.CommonDateFormat.format(this)
    return nowDateString == currentDateString
}

private fun Date.isYesterday(): Boolean {
    val previousDate = Calendar.getInstance().apply {
        time = Date()
        add(Calendar.DAY_OF_YEAR, -1)
    }.time
    val currentDateString = DateFormatType.CommonDateFormat.format(this)
    val previousDateString = DateFormatType.CommonDateFormat.format(previousDate)
    return previousDateString == currentDateString
}

private fun Date.isCurrentYear(): Boolean {
    val nowCalendar = Calendar.getInstance().apply { time = Date() }
    val currentYear = nowCalendar.get(Calendar.YEAR)

    val dateCalendar = Calendar.getInstance().apply { time = this@isCurrentYear }
    val dateYear = dateCalendar.get(Calendar.YEAR)

    return currentYear == dateYear
}