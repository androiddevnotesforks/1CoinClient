package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

data class Account(
    val id: Long,
    val type: Type,
    val name: String,
    val balance: Amount,
    val colorModel: AccountColorModel
) {
    enum class Type(
        val textId: StringResource,
        val analyticsName: String
    ) {
        Cash(
            textId = MR.strings.account_type_cash,
            analyticsName = "Cash"
        ),
        Card(
            textId = MR.strings.account_type_card,
            analyticsName = "Card"
        ),
        BankAccount(
            textId = MR.strings.account_type_bank_account,
            analyticsName = "BankAccount"
        ),
        Credit(
            textId = MR.strings.account_type_credit,
            analyticsName = "Credit"
        ),
        Deposit(
            textId = MR.strings.account_type_deposit,
            analyticsName = "Deposit"
        ),
        Investment(
            textId = MR.strings.account_type_investment_account,
            analyticsName = "Investment"
        );

        companion object {
            fun fromName(name: String): Type {
                return when (name) {
                    "Cash" -> Cash
                    "Card" -> Card
                    "BankAccount" -> BankAccount
                    "Credit" -> Credit
                    "Deposit" -> Deposit
                    "Investment" -> Investment
                    else -> error("No account type for name: $name")
                }
            }
        }
    }

    val icon: ImageResource
        get() = if (type == Type.Cash) {
            MR.images.ic_wallet_active
        } else {
            MR.images.ic_card
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