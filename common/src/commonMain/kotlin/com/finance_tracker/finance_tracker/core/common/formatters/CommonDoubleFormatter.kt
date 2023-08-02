package com.finance_tracker.finance_tracker.core.common.formatters

expect fun Double.format(): String

expect fun String.parseToDouble(): Double?

expect fun getNumberSeparatorSymbol(): String