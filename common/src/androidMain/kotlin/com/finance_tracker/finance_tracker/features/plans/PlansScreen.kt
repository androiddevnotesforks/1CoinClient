package com.finance_tracker.finance_tracker.features.plans

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.features.plans.views.BudgetByCategoriesWidget
import com.finance_tracker.finance_tracker.features.plans.views.PlansTopBar

@Composable
fun PlansScreen() {
    ComposeScreen<PlansViewModel>(withBottomNavigation = true) { viewModel ->

        Column(modifier = Modifier.fillMaxSize()) {
            PlansTopBar()

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                BudgetByCategoriesWidget()

                Spacer(
                    modifier = Modifier
                        .padding(bottom = CoinPaddings.bottomNavigationBar)
                        .navigationBarsPadding()
                )
            }
        }
    }
}