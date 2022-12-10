package com.finance_tracker.finance_tracker.domain.models

data class Account(
    val id: Long,
    val type: Type,
    val name: String,
    val balance: Amount,
    val colorModel: AccountColorModel
) {
    enum class Type(val textId: String) {
        Cash("account_type_cash"),
        Card("account_type_card"),
        BankAccount("account_type_bank_account"),
        Credit("account_type_credit"),
        Deposit("account_type_deposit")
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