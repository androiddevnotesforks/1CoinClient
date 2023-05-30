package com.finance_tracker.finance_tracker.features.plans.setup

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.resources.StringResource

@Parcelize
enum class SetupPlanStep(val textId: StringResource? = null): Parcelable {
    Category(MR.strings.add_transaction_stage_category),
    PrimaryAmount;
}