package com.finance_tracker.finance_tracker.features.analytics.models

import com.finance_tracker.finance_tracker.core.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.domain.models.Amount
import dev.icerock.moko.resources.desc.StringDesc

data class TrendBarDetails(
    val title: () -> StringDesc,
    val provideAmountWithFormat: () -> Pair<Amount, AmountFormatMode>,
    val value: Double
)