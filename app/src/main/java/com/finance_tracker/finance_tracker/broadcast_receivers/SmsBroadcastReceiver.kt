package com.finance_tracker.finance_tracker.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import com.finance_tracker.finance_tracker.App
import com.finance_tracker.finance_tracker.data.models.Transaction
import com.finance_tracker.finance_tracker.parsers.SmsMessageParser
import com.finance_tracker.finance_tracker.parsers.templates.tinkoffSmsTemplate
import com.finance_tracker.finance_tracker.toTransactionTypeEntity


class SmsBroadcastReceiver : BroadcastReceiver() {

    private val database = App.database
    private val smsMessageParser = SmsMessageParser(
        templates = listOf(tinkoffSmsTemplate)
    )

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extractMessages.forEach { smsMessage ->
            val transaction = smsMessageParser.parseMessage(smsMessage)

            if (transaction != null) {
                insertTransactionIntoDatabase(transaction)
            }
            insertSmsMessageIntoDatabase(
                smsMessage = smsMessage,
                hasTransaction = transaction != null
            )
        }
    }

    private fun insertTransactionIntoDatabase(transaction: Transaction) {
        database.transactionsEntityQueries.insertTransaction(
            id = null,
            type = transaction.type.toTransactionTypeEntity(),
            amount = transaction.amount,
            amountCurrency = transaction.amountCurrency,
            category = transaction.category,
            accountId = null, // TODO: Создать отдельную таблицу для хранения подключенных счетов и ссылаться на записи в той таблице
            date = transaction.date?.time ?: 0
        )
    }

    private fun insertSmsMessageIntoDatabase(smsMessage: SmsMessage, hasTransaction: Boolean) {
        database.smsMessageEntityQueries.insertSmsMessage(
            id = null,
            sender = smsMessage.displayOriginatingAddress.orEmpty(),
            message = smsMessage.displayMessageBody.orEmpty(),
            date = smsMessage.timestampMillis,
            isRecognized = hasTransaction
        )
    }
}