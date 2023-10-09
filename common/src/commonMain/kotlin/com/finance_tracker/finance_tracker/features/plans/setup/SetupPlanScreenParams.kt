package com.finance_tracker.finance_tracker.features.plans.setup

import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.presentation.models.PlanSerializable

data class SetupPlanScreenParams(
    val yearMonth: YearMonth,
    val plan: PlanSerializable? = null
)