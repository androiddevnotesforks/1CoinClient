package com.finance_tracker.finance_tracker.features.plans.setup

import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.domain.models.Plan

data class SetupPlanScreenParams(
    val yearMonth: YearMonth,
    val plan: Plan? = null
)