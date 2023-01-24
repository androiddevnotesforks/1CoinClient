package com.finance_tracker.finance_tracker.presentation.dashboard_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.common.StoredViewModel

@Composable
fun DashboardSettingsScreen() {
    StoredViewModel<DashboardSettingsViewModel> { viewModel ->
        Column {
            Text(
                text = "DashboardSettingsScreen"
            )
        }
    }
}