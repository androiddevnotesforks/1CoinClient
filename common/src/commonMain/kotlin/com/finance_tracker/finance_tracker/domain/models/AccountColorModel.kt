package com.finance_tracker.finance_tracker.domain.models

import androidx.compose.ui.graphics.Color

@Suppress("MagicNumber")
enum class AccountColorModel(
    val id: Int,
    val color: Color,
    val colorName: String
) {
    EastBay(
        id = 0,
        color = Color(0xFF4B3F72),
        colorName = "East Bay"
    ),
    RobinsEggBlue(
        id = 1,
        color = Color(0xFF07BEB8),
        colorName = "Robin's Egg Blue"
    ),
    GoldenTainoi(
        id = 2,
        color = Color(0xFFFFC857),
        colorName = "Golden Tainoi"
    ),
    CopperRose(
        id = 3,
        color = Color(0xFF9B6A6C),
        colorName = "Copper Rose"
    ),
    Bouquet(
        id = 4,
        color = Color(0xFFB37BA4),
        colorName = "Bouquet"
    ),
    DeepBlush(
        id = 5,
        color = Color(0xFFE36588),
        colorName = "Deep Blush"
    ),
    BreakerBay(
        id = 6,
        color = Color(0xFF519E8A),
        colorName = "Breaker Bay"
    ),
    RedDamask(
        id = 7,
        color = Color(0xFFD56F3E),
        colorName = "Red Damask"
    ),
    Elm(
        id = 8,
        color = Color(0xFF1F7A8C),
        colorName = "Elm"
    ),
    Stack(
        id = 9,
        color = Color(0xFF838E83),
        colorName = "Stack"
    );

    companion object {

        val defaultAccountColor = EastBay

        fun from(id: Int): AccountColorModel {
            return AccountColorModel.values()
                .firstOrNull { it.id == id } ?: defaultAccountColor
        }
    }
}