package com.finance_tracker.finance_tracker.domain.models

import androidx.compose.ui.graphics.Color

data class TxsByCategoryChart(
    val pieces: List<Piece>,
    val total: Double
) {
    data class Piece(
        val category: Category?,
        val amount: Double,
        val color: Color
    )
}