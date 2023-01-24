package com.finance_tracker.finance_tracker.presentation.dashboard_settings

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.presentation.dashboard_settings.analytics.DashboardSettingsAnalytics

class DashboardSettingsViewModel(
    dashboardSettingsAnalytics: DashboardSettingsAnalytics
): BaseViewModel<DashboardSettingsAction>() {

    init {
        dashboardSettingsAnalytics.trackScreenOpen()
    }
}