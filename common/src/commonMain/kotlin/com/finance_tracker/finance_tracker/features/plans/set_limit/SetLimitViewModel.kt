package com.finance_tracker.finance_tracker.features.plans.set_limit

import com.finance_tracker.finance_tracker.core.common.TextFieldValue
import com.finance_tracker.finance_tracker.core.common.TextRange
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.common.formatters.parseToDouble
import com.finance_tracker.finance_tracker.core.common.keyboard.KeyboardAction
import com.finance_tracker.finance_tracker.core.common.keyboard.applyKeyboardAction
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.plans.PlansInteractor
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.features.plans.overview.analytics.PlansOverviewAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SetLimitViewModel(
    currenciesInteractor: CurrenciesInteractor,
    private val plansInteractor: PlansInteractor,
    private val plansOverviewAnalytics: PlansOverviewAnalytics
): BaseViewModel<SetLimitAction>() {

    private var dialogKey: String? = null
    private var yearMonth: YearMonth? = null
    private val enteredBalance = MutableStateFlow(
        TextFieldValue(
            text = "",
            selection = TextRange.Zero
        )
    )
    private val primaryCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, initialValue = Currency.default)

    val state = combine(enteredBalance, primaryCurrency) { enteredBalance, primaryCurrency ->
        SetLimitState(
            balance = enteredBalance,
            primaryCurrency = primaryCurrency
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, initialValue = SetLimitState.createDefault())

    fun setYearMonth(yearMonth: YearMonth) {
        this.yearMonth = yearMonth
        plansInteractor.getMonthLimitAmount(yearMonth)
            .onEach {
                val text = if (it.amountValue != 0.0) {
                    it.amountValue.format()
                } else {
                    ""
                }
                enteredBalance.value = TextFieldValue(
                    text = text,
                    selection = TextRange(text.length, text.length),
                )
            }
            .launchIn(viewModelScope)
    }

    fun setDialogKey(dialogKey: String) {
        this.dialogKey = dialogKey
    }

    fun onAmountChange(amount: TextFieldValue) {
        enteredBalance.value = amount
    }

    fun onKeyboardButtonClick(keyboardAction: KeyboardAction) {
        enteredBalance.applyKeyboardAction(keyboardAction)
    }

    fun onSetLimitClick() {
        yearMonth?.let { yearMonth ->
            viewModelScope.launch {
                val limitValue = enteredBalance.value.text.parseToDouble() ?: return@launch
                val amount = Amount(primaryCurrency.value, limitValue)
                plansOverviewAnalytics.trackSetLimitClick(yearMonth)
                plansInteractor.setMonthLimit(yearMonth, amount)
                dialogKey?.let {
                    viewAction = SetLimitAction.DismissDialog(it)
                }
            }
        }
    }
}