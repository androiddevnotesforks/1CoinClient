package com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers

import android.telephony.SmsMessage
import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models.SmsTemplate
import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models.TransactionFields
import com.finance_tracker.finance_tracker.domain.models.Transaction

@Suppress("UnusedPrivateMember")
class SmsMessageParser(
    private val templates: List<SmsTemplate>
) {

    fun parseMessage(smsMessage: SmsMessage): Transaction? {
        /*val date = Date(smsMessage.timestampMillis)
        val message = smsMessage.displayMessageBody
        val supportedTemplate = getSupportedTemplate(smsMessage) ?: return null

        val fields = groupByTransactionFields(message, supportedTemplate)
        return Transaction(
            id = -1,
            type = fields.getTransactionType() ?: return null,
            amount = Amount(
                currency = Currency.getByCode(fields.getAmountCurrency()),
                amountValue = fields.getAmount()
            ),
            account = Account(
                id = 1,
                type = Account.Type.Card,
                name = "Fake card",
                balance = Amount(
                    amountValue = 0.0,
                    currency = Currency.default
                ),
                colorModel = AccountColorModel.defaultAccountColor
            ),
            category = null,
            date = date,
            insertionDate = Date()
        )*/
        return Transaction.EMPTY
    }

    private fun getSupportedTemplate(smsMessage: SmsMessage): SmsTemplate? {
        val message = smsMessage.displayMessageBody
        val fromAddress = smsMessage.originatingAddress
        return templates
            .firstOrNull { fromAddress in it.fromAddresses && it.regex.matches(message) }
    }

    private fun groupByTransactionFields(
        message: String,
        smsTemplate: SmsTemplate
    ): TransactionFields {
        val regex = smsTemplate.regex
        val groupNames = smsTemplate.transactionFields

        val groupValues = regex.findAll(message).first()
            .groups.toMutableList()
            .apply { removeAt(0) }
            .mapNotNull { it?.value }
        return groupNames.zip(groupValues).toMap()
    }
}