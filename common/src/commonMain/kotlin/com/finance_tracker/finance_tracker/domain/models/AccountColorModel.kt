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
        colorName = "Purple"
    ),
    RobinsEggBlue(
        id = 1,
        color = Color(0xFF07BEB8),
        colorName = "Blue"
    ),
    GoldenTainoi(
        id = 2,
        color = Color(0xFFFFC857),
        colorName = "Yellow"
    ),
    CopperRose(
        id = 3,
        color = Color(0xFF9B6A6C),
        colorName = "Brown"
    ),
    Bouquet(
        id = 4,
        color = Color(0xFFB37BA4),
        colorName = "Lavender"
    ),
    DeepBlush(
        id = 5,
        color = Color(0xFFE36588),
        colorName = "Pink"
    ),
    BreakerBay(
        id = 6,
        color = Color(0xFF519E8A),
        colorName = "Green"
    ),
    RedDamask(
        id = 7,
        color = Color(0xFFD56F3E),
        colorName = "Orange"
    ),
    Elm(
        id = 8,
        color = Color(0xFF1F7A8C),
        colorName = "Seaweed"
    ),
    Stack(
        id = 9,
        color = Color(0xFF838E83),
        colorName = "Grey"
    );

    companion object {

        val defaultAccountColor = EastBay

        fun from(id: Int): AccountColorModel {
            return AccountColorModel.values()
                .firstOrNull { it.id == id } ?: defaultAccountColor
        }
    }
}