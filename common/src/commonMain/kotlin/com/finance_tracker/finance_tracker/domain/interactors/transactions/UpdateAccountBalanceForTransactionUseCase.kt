package com.finance_tracker.finance_tracker.domain.interactors.transactions

import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType

class UpdateAccountBalanceForTransactionUseCase(
    private val accountsRepository: AccountsRepository
) {

    suspend fun invoke(
        oldTransaction: Transaction?,
        newTransaction: Transaction?
    ) {
        updatePrimaryAccountBalance(oldTransaction, newTransaction)
        updateSecondaryAccountBalance(oldTransaction, newTransaction)
    }

    private suspend fun updatePrimaryAccountBalance(
        oldTransaction: Transaction?,
        newTransaction: Transaction?
    ) {
        val transaction = oldTransaction ?: newTransaction ?: return

        val oldTransactionPrimaryAmount = oldTransaction.signedAmountValue()
        val newTransactionPrimaryAmount = newTransaction.signedAmountValue()
        val oldPrimaryAccountId = oldTransaction?.primaryAccount?.id
        val newPrimaryAccountId = newTransaction?.primaryAccount?.id

        if (
            newPrimaryAccountId != null && oldPrimaryAccountId != null &&
            oldPrimaryAccountId != newPrimaryAccountId
        ) {
            // Accounts are changed
            updateAccountsBalance(
                oldAccountId = oldPrimaryAccountId,
                oldTransactionAmount = oldTransactionPrimaryAmount,
                newAccountId = newPrimaryAccountId,
                newTransactionAmount = newTransactionPrimaryAmount
            )
        } else {
            // Accounts aren't changed
            val primaryAccountId = transaction.primaryAccount.id
            updateAccountBalance(
                accountId = primaryAccountId,
                oldTransactionAmount = oldTransactionPrimaryAmount,
                newTransactionAmount = newTransactionPrimaryAmount
            )
        }
    }

    private suspend fun updateSecondaryAccountBalance(
        oldTransaction: Transaction?,
        newTransaction: Transaction?
    ) {
        val transaction = oldTransaction ?: newTransaction ?: return
        val secondaryAccountId = transaction.secondaryAccount?.id ?: return

        val oldTransactionSecondaryAmount = oldTransaction.secondaryAmountValueOrZero()
        val newTransactionSecondaryAmount = newTransaction.secondaryAmountValueOrZero()

        val oldSecondaryAccountId = oldTransaction?.secondaryAccount?.id
        val newSecondaryAccountId = newTransaction?.secondaryAccount?.id

        if (
            newSecondaryAccountId != null && oldSecondaryAccountId != null &&
            oldSecondaryAccountId != newSecondaryAccountId
        ) {
            // Accounts are changed
            updateAccountsBalance(
                oldAccountId = oldSecondaryAccountId,
                oldTransactionAmount = oldTransactionSecondaryAmount,
                newAccountId = newSecondaryAccountId,
                newTransactionAmount = newTransactionSecondaryAmount
            )
        } else {
            // Accounts aren't changed
            updateAccountBalance(
                accountId = secondaryAccountId,
                oldTransactionAmount = oldTransactionSecondaryAmount,
                newTransactionAmount = newTransactionSecondaryAmount
            )
        }
    }

    private suspend fun updateAccountsBalance(
        oldAccountId: Long,
        oldTransactionAmount: Double,
        newAccountId: Long,
        newTransactionAmount: Double,
    ) {
        accountsRepository.decreaseAccountBalance(oldAccountId, oldTransactionAmount)
        accountsRepository.increaseAccountBalance(newAccountId, newTransactionAmount)
    }

    private suspend fun updateAccountBalance(
        accountId: Long,
        oldTransactionAmount: Double,
        newTransactionAmount: Double,
    ) {
        val accountBalanceDiff = newTransactionAmount - oldTransactionAmount
        accountsRepository.increaseAccountBalance(accountId, accountBalanceDiff)
    }

    private fun Transaction?.signedAmountValue(): Double {
        if (this == null) return 0.0

        return primaryAmount.amountValue.unaryMinusIf {
            type == TransactionType.Expense || type == TransactionType.Transfer
        }
    }

    private fun Transaction?.secondaryAmountValueOrZero(): Double {
        return this?.secondaryAmount?.amountValue ?: 0.0
    }

    private fun Double.unaryMinusIf(condition: () -> Boolean): Double {
        return if (condition()) {
            unaryMinus()
        } else {
            this
        }
    }
}