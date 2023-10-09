package com.finance_tracker.finance_tracker.features.plans.setup

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.StringResource
import kotlinx.serialization.Serializable

@Serializable
enum class SetupPlanStep(val textId: StringResource? = null) {
    Category(MR.strings.add_transaction_stage_category),
    PrimaryAmount;
}