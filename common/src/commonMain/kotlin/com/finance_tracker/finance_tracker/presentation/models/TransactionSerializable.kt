package com.finance_tracker.finance_tracker.presentation.models

import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import kotlinx.serialization.Serializable

@Serializable
data class TransactionSerializable(
    val id: Long? = null,
    val type: TypeSerializable,
    val primaryAccount: AccountSerializable,
    val secondaryAccount: AccountSerializable? = null,
    val primaryAmount: AmountSerializable = Amount.default.toSerializable(),
    val secondaryAmount: AmountSerializable? = null,
    val category: CategorySerializable? = null,
    val dateTime: LocalDateTimeSerializable,
    val insertionDateTime: LocalDateTimeSerializable?
) {

    @Serializable
    enum class TypeSerializable {
        Expense,
        Income,
        Transfer;
    }
}

fun TransactionType.toSerializable(): TransactionSerializable.TypeSerializable {
    return when (this) {
        TransactionType.Expense -> TransactionSerializable.TypeSerializable.Expense
        TransactionType.Income -> TransactionSerializable.TypeSerializable.Income
        TransactionType.Transfer -> TransactionSerializable.TypeSerializable.Transfer
    }
}

fun TransactionSerializable.TypeSerializable.toDomain(): TransactionType {
    return when (this) {
        TransactionSerializable.TypeSerializable.Expense -> TransactionType.Expense
        TransactionSerializable.TypeSerializable.Income -> TransactionType.Income
        TransactionSerializable.TypeSerializable.Transfer -> TransactionType.Transfer
    }
}

// map LocalDateTime to isoString




fun Transaction.toSerializable(): TransactionSerializable {
    return TransactionSerializable(
        id = id,
        type = type.toSerializable(),
        primaryAccount = primaryAccount.toSerializable(),
        secondaryAccount = secondaryAccount?.toSerializable(),
        primaryAmount = primaryAmount.toSerializable(),
        secondaryAmount = secondaryAmount?.toSerializable(),
        category = _category?.toSerializable(),
        dateTime = dateTime.toSerializable(),
        insertionDateTime = insertionDateTime?.toSerializable()
    )
}

fun TransactionSerializable.toDomain(): Transaction {
    return Transaction(
        id = id,
        type = type.toDomain(),
        primaryAccount = primaryAccount.toDomain(),
        secondaryAccount = secondaryAccount?.toDomain(),
        primaryAmount = primaryAmount.toDomain(),
        secondaryAmount = secondaryAmount?.toDomain(),
        _category = category?.toDomain(),
        dateTime = dateTime.toDomain(),
        insertionDateTime = insertionDateTime?.toDomain()
    )
}
