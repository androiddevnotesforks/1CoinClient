package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.core.common.date.currentLocalDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "TransactionModel")
data class Transaction(
    val id: Long? = null,
    val type: TransactionType,
    val primaryAccount: Account,
    val secondaryAccount: Account? = null,
    val primaryAmount: Amount = Amount.default,
    val secondaryAmount: Amount? = null,
    @Suppress("ConstructorParameterNaming")
    val _category: Category? = null,
    val dateTime: LocalDateTime,
    val insertionDateTime: LocalDateTime?,
) {

    fun getCategoryOrUncategorized(): Category {
        return _category ?: Category.empty()
    }

    companion object {
        val EMPTY = Transaction(
            id = null,
            type = TransactionType.Expense,
            primaryAccount = Account.EMPTY,
            primaryAmount = Amount.default,
            _category = null,
            dateTime = Clock.System.currentLocalDateTime(),
            insertionDateTime = null
        )
    }
}