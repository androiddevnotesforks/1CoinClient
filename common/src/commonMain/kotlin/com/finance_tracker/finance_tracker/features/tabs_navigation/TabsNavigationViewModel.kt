package com.finance_tracker.finance_tracker.features.tabs_navigation

import com.finance_tracker.finance_tracker.core.common.view_models.ComponentViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import kotlinx.coroutines.launch

class TabsNavigationViewModel(
    private val currenciesInteractor: CurrenciesInteractor
): ComponentViewModel<Any, TabsNavigationComponent.Action>() {

    init {
        checkPrimaryCurrencySelected()
    }

    private fun checkPrimaryCurrencySelected() {
        viewModelScope.launch {
            if (!currenciesInteractor.isPrimaryCurrencySelected()) {
                componentAction = TabsNavigationComponent.Action.OpenPresetCurrencyScreen
            }
        }
    }

    fun onDismissBottomDialog() {
        componentAction = TabsNavigationComponent.Action.DismissBottomDialog
    }
}