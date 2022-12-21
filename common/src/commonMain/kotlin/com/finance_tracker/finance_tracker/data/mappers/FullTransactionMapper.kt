package com.finance_tracker.finance_tracker.data.mappers

import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import java.util.Date

val fullTransactionMapper: (
    id: Long,
    type: TransactionType,
    amount: Double,
    amountCurrency: String,
    categoryId: Long?,
    accountId: Long?,
    insertionDate: Date,
    date: Date,
    id_: Long,
    type_: Account.Type,
    name: String,
    balance: Double,
    colorId: Int,
    currency: String,
    id__: Long,
    name_: String,
    icon: String,
    position: Long?,
    isExpense: Boolean,
    isIncome: Boolean
) -> Transaction = { id, type, amount, amountCurrency, categoryId,
                     accountId, insertionDate, date, _, accountType, accountName, balance,
                     accountColorId, _, _, categoryName, categoryIcon, _, _, _ ->
    val currency = Currency.getByCode(amountCurrency)
    Transaction(
        id = id,
        type = type,
        account = Account(
            id = accountId ?: 0L,
            type = accountType,
            colorModel = AccountColorModel.from(accountColorId),
            name = accountName,
            balance = Amount(
                amountValue = balance,
                currency = currency
            )
        ),
        category = categoryId?.let {
            Category(
                id = categoryId,
                name = categoryName,
                iconId = categoryIcon
            )
        },
        amount = Amount(
            currency = currency,
            amountValue = amount
        ),
        date = date,
        insertionDate = insertionDate
    )
}