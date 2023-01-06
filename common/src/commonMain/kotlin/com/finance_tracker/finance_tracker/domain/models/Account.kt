package com.finance_tracker.finance_tracker.domain.models

data class Account(
    val id: Long,
    val type: Type,
    val name: String,
    val balance: Amount,
    val colorModel: AccountColorModel
) {
    enum class Type(
        val textId: String,
        val analyticsName: String
    ) {
        Cash(
            textId = "account_type_cash",
            analyticsName = "Cash"
        ),
        Card(
            textId = "account_type_card",
            analyticsName = "Card"
        ),
        BankAccount(
            textId = "account_type_bank_account",
            analyticsName = "BankAccount"
        ),
        Credit(
            textId = "account_type_credit",
            analyticsName = "Credit"
        ),
        Deposit(
            textId = "account_type_deposit",
            analyticsName = "Deposit"
        )
    }

    val iconId: String
        get() = if (type == Type.Cash) {
            "ic_wallet_active"
        } else {
            "ic_card"
        }

    companion object {
        val EMPTY = Account(
            id = -1,
            type = Type.Cash,
            name = "",
            balance = Amount(
                currency = Currency.default,
                amountValue = 0.0
            ),
            colorModel = AccountColorModel.defaultAccountColor
        )
    }
}