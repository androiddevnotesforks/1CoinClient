package com.finance_tracker.finance_tracker.features.analytics.peroid_bar_chart

import dev.icerock.moko.resources.desc.StringDesc
import io.github.koalaplot.core.bar.BarChartEntry

data class CoinBarChartEntry<X, Y>(
    override val xValue: X,
    override val yMin: Y,
    override val yMax: Y,
    val overviewTitle: StringDesc,
    val overviewValue: String
) : BarChartEntry<X, Y>
