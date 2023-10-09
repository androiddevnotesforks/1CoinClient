package com.finance_tracker.finance_tracker.presentation.models

import com.finance_tracker.finance_tracker.domain.models.Account
import kotlinx.serialization.Serializable

@Serializable
data class AccountSerializable(
    val id: Long,
    val type: TypeSerializable,
    val name: String,
    val balance: AmountSerializable,
    val colorModel: AccountColorSerializable
) {

    @Serializable
    enum class TypeSerializable {
        Cash,
        Card,
        BankAccount,
        Credit,
        Deposit,
        Investment;
    }
}

fun Account.Type.toSerializable(): AccountSerializable.TypeSerializable {
    return when (this) {
        Account.Type.Cash -> AccountSerializable.TypeSerializable.Cash
        Account.Type.Card -> AccountSerializable.TypeSerializable.Card
        Account.Type.BankAccount -> AccountSerializable.TypeSerializable.BankAccount
        Account.Type.Credit -> AccountSerializable.TypeSerializable.Credit
        Account.Type.Deposit -> AccountSerializable.TypeSerializable.Deposit
        Account.Type.Investment -> AccountSerializable.TypeSerializable.Investment
    }
}

fun AccountSerializable.TypeSerializable.toDomain(): Account.Type {
    return when (this) {
        AccountSerializable.TypeSerializable.Cash -> Account.Type.Cash
        AccountSerializable.TypeSerializable.Card -> Account.Type.Card
        AccountSerializable.TypeSerializable.BankAccount -> Account.Type.BankAccount
        AccountSerializable.TypeSerializable.Credit -> Account.Type.Credit
        AccountSerializable.TypeSerializable.Deposit -> Account.Type.Deposit
        AccountSerializable.TypeSerializable.Investment -> Account.Type.Investment
    }
}

fun Account.toSerializable(): AccountSerializable {
    return AccountSerializable(
        id = id,
        type = type.toSerializable(),
        name = name,
        balance = balance.toSerializable(),
        colorModel = colorModel.toSerializable()
    )
}

fun AccountSerializable.toDomain(): Account {
    return Account(
        id = id,
        type = type.toDomain(),
        name = name,
        balance = balance.toDomain(),
        colorModel = colorModel.toDomain()
    )
}