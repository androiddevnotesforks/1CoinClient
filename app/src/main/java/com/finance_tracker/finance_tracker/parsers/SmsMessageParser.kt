package com.finance_tracker.finance_tracker.parsers

import android.telephony.SmsMessage
import com.finance_tracker.finance_tracker.data.models.Transaction
import com.finance_tracker.finance_tracker.parsers.models.SmsTemplate
import com.finance_tracker.finance_tracker.parsers.models.TransactionFields
import com.finance_tracker.finance_tracker.parsers.models.getAmount
import com.finance_tracker.finance_tracker.parsers.models.getAmountCurrency
import com.finance_tracker.finance_tracker.parsers.models.getCardNumber
import com.finance_tracker.finance_tracker.parsers.models.getTransactionType
import java.util.Date

class SmsMessageParser(
    private val templates: List<SmsTemplate>
) {

    fun parseMessage(smsMessage: SmsMessage): Transaction? {
        val date = Date(smsMessage.timestampMillis)
        val message = smsMessage.displayMessageBody
        val supportedTemplate = getSupportedTemplate(smsMessage) ?: return null

        val fields = groupByTransactionFields(message, supportedTemplate)
        return Transaction(
            type = fields.getTransactionType() ?: return null,
            amount = fields.getAmount(),
            amountCurrency = fields.getAmountCurrency(),
            category = null,
            cardNumber = fields.getCardNumber(),
            date = date
        )
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