package com.finance_tracker.finance_tracker.data.mocks

import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.data.models.Account

object MockAccounts {
    val myAccounts = listOf(
        /*Account(
            type = Account.Type.Cash,
            name = "Cash - 1",
            color = Color.Red
        ),
        Account(
            type = Account.Type.Cash,
            name = "Cash - 2",
            color = Color.Blue
        ),
        Account(
            type = Account.Type.Cash,
            name = "Cash - 3",
            color = Color.Cyan
        ),*/
        Account(
            type = Account.Type.DebitCard,
            name = "Debit - 1",
            color = Color.Gray
        ),
        Account(
            type = Account.Type.DebitCard,
            name = "Debit - 2",
            color = Color.LightGray
        ),
        Account(
            type = Account.Type.CreditCard,
            name = "Credit - 1",
            color = Color.Yellow
        )
    )
}