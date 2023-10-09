package com.finance_tracker.finance_tracker.features.analytics

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel

class AnalyticsComponent(
    componentContext: ComponentContext
): BaseComponent<AnalyticsViewModel>(componentContext) {
    override val viewModel: AnalyticsViewModel = injectViewModel()
}