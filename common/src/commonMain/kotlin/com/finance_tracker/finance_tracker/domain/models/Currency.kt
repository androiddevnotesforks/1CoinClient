package com.finance_tracker.finance_tracker.domain.models

data class Currency(
    val code: String,
    val symbol: String
) {
    companion object {

        private val USD = Currency(code = "USD", symbol = "\$")
        private val EUR = Currency(code = "EUR", symbol = "€")
        private val RUB = Currency(code = "RUB", symbol = "₽")
        private val TRY = Currency(code = "TRY", symbol = "₺")

        val list = listOf(USD, EUR, RUB, TRY)
        val default = USD

        private val map = list.associateBy { it.code }

        fun getByCode(name: String): Currency {
            return map[name]!!
        }
    }
}