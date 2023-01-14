package com.finance_tracker.finance_tracker.core.common.number_formatters

import androidx.compose.runtime.Composable

@Composable
expect fun formatAmount(number: Double, currencyCode: String): String