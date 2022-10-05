package com.finance_tracker.finance_tracker.data.mocks

import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.data.models.Account
import com.finance_tracker.finance_tracker.data.models.Transaction
import java.util.Date

object MockTransactions {
    val myTransactions = mutableListOf<Transaction>().apply {
        repeat(100) {
            add(
                Transaction(
                    type = Transaction.Type.Expense,
                    amountCurrency = "$",
                    account = Account(
                        type = Account.Type.DebitCard,
                        name = "12",
                        color = Color.Red
                    ),
                    amount = 10.5,
                    category = "1234",
                    cardNumber = "123456",
                    date = Date()
                )
            )
        }
    }
}