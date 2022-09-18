package com.finance_tracker.finance_tracker.parsers.templates

import com.finance_tracker.finance_tracker.parsers.models.SmsTemplate
import com.finance_tracker.finance_tracker.parsers.models.TransactionField

val tinkoffSmsTemplate = SmsTemplate(
    fromAddresses = listOf("Tinkoff"),
    pattern = "${TransactionField.Type.template}, " +
            "карта ${TransactionField.CardNumber.template}. " +
            "${TransactionField.Amount.template} ${TransactionField.AmountCurrency.template}. " +
            "${TransactionField.Place.template}. Доступно .*"
)