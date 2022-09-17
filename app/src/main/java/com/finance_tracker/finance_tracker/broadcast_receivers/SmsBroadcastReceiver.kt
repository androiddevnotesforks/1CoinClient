package com.finance_tracker.finance_tracker.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.finance_tracker.finance_tracker.parsers.SmsMessageParser
import com.finance_tracker.finance_tracker.parsers.templates.tinkoffSmsTemplate


class SmsBroadcastReceiver : BroadcastReceiver() {

    private val smsMessageParser = SmsMessageParser(
        templates = listOf(tinkoffSmsTemplate)
    )

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extractMessages.forEach { smsMessage ->
            val transaction = smsMessageParser.parseMessage(smsMessage)
            // TODO: Сохранение smsMessage в БД
            // TODO: Сохранение транзакции в БД
            Log.d("mLog", "transaction: $transaction")
        }
    }
}