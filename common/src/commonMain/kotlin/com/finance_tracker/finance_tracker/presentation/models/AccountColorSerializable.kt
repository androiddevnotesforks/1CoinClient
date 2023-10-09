package com.finance_tracker.finance_tracker.presentation.models

import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import kotlinx.serialization.Serializable

@Suppress("MagicNumber")
@Serializable
enum class AccountColorSerializable {
    EastBay,
    RobinsEggBlue,
    GoldenTainoi,
    CopperRose,
    Bouquet,
    DeepBlush,
    BreakerBay,
    RedDamask,
    Elm,
    Stack;
}

fun AccountColorModel.toSerializable(): AccountColorSerializable {
    return when (this) {
        AccountColorModel.EastBay -> AccountColorSerializable.EastBay
        AccountColorModel.RobinsEggBlue -> AccountColorSerializable.RobinsEggBlue
        AccountColorModel.GoldenTainoi -> AccountColorSerializable.GoldenTainoi
        AccountColorModel.CopperRose -> AccountColorSerializable.CopperRose
        AccountColorModel.Bouquet -> AccountColorSerializable.Bouquet
        AccountColorModel.DeepBlush -> AccountColorSerializable.DeepBlush
        AccountColorModel.BreakerBay -> AccountColorSerializable.BreakerBay
        AccountColorModel.RedDamask -> AccountColorSerializable.RedDamask
        AccountColorModel.Elm -> AccountColorSerializable.Elm
        AccountColorModel.Stack -> AccountColorSerializable.Stack
    }
}

fun AccountColorSerializable.toDomain(): AccountColorModel {
    return when (this) {
        AccountColorSerializable.EastBay -> AccountColorModel.EastBay
        AccountColorSerializable.RobinsEggBlue -> AccountColorModel.RobinsEggBlue
        AccountColorSerializable.GoldenTainoi -> AccountColorModel.GoldenTainoi
        AccountColorSerializable.CopperRose -> AccountColorModel.CopperRose
        AccountColorSerializable.Bouquet -> AccountColorModel.Bouquet
        AccountColorSerializable.DeepBlush -> AccountColorModel.DeepBlush
        AccountColorSerializable.BreakerBay -> AccountColorModel.BreakerBay
        AccountColorSerializable.RedDamask -> AccountColorModel.RedDamask
        AccountColorSerializable.Elm -> AccountColorModel.Elm
        AccountColorSerializable.Stack -> AccountColorModel.Stack
    }
}