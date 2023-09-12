package com.finance_tracker.finance_tracker.features.plans.setup

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.isNotEmptyAmount
import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.core.common.toString
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.hideSnackbar
import com.finance_tracker.finance_tracker.core.common.view_models.showPreviousScreenSnackbar
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarActionState
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarState
import com.finance_tracker.finance_tracker.domain.interactors.CategoriesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.plans.PlansInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.finance_tracker.finance_tracker.features.add_transaction.KeyboardCommand
import com.finance_tracker.finance_tracker.features.add_transaction.applyKeyboardCommand
import com.finance_tracker.finance_tracker.features.plans.setup.analytics.SetupPlanAnalytics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MaxAmountValue = 100_000_000_000_000.0
private const val MaxAmountLength = 18

class SetupPlanViewModel(
    params: SetupPlanScreenParams,
    private val categoriesInteractor: CategoriesInteractor,
    private val setupPlanAnalytics: SetupPlanAnalytics,
    private val plansInteractor: PlansInteractor,
    currenciesInteractor: CurrenciesInteractor
): BaseViewModel<SetupPlanAction>() {

    private val yearMonth: YearMonth = params.yearMonth
    private val plan: Plan? = params.plan

    val isEditMode = plan != null

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    val primaryCurrency = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = Currency.default)

    private val initialAmount = plan?.limitAmount
    private val _primaryAmountText: MutableStateFlow<String> = MutableStateFlow(
        initialAmount?.amountValue?.toString(precision = 2) ?: "0"
    )
    val primaryAmountText: StateFlow<String> = _primaryAmountText.asStateFlow()

    private val _expenseCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val expenseCategories: StateFlow<List<Category>> = _expenseCategories.asStateFlow()

    private val setupPlanFlow = listOf(
        SetupPlanStep.Category,
        SetupPlanStep.PrimaryAmount
    )
    private val currentFlowState = SetupPlanFlowState(setupPlanFlow)
    val currentFlow = currentFlowState.steps

    private val _selectedCategory = MutableStateFlow(plan?.category)
    val selectedCategory = _selectedCategory.asStateFlow()

    val currentStep = currentFlowState.currentStepFlow
        .stateIn(viewModelScope, initialValue = null)

    private val hasPreviousStep = currentFlowState.hasPreviousStep

    @Suppress("UnnecessaryParentheses")
    val isAddTransactionEnabled = combine(
        selectedCategory, primaryAmountText
    ) { selectedCategory, primaryAmountText ->
        selectedCategory != null && primaryAmountText.isNotEmptyAmount()
    }
        .stateIn(viewModelScope, initialValue = false)

    init {
        setupPlanAnalytics.trackSetupPlanScreenOpen()
        setCurrentStep()
    }

    private fun setCurrentStep() {
        currentFlowState.setCurrentStepForFlowState(
            isExpenseCategoryEmpty = selectedCategory.value == null,
        )
    }

    fun onScreenComposed() {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _expenseCategories.value = categoriesInteractor.getAllExpenseCategories()
        }
    }

    fun onAddPlanClick(
        plan: Plan,
        isFromButtonClick: Boolean
    ) {
        setupPlanAnalytics.trackAddClick(isFromButtonClick, plan)
        viewModelScope.launch {
            plansInteractor.addPlan(
                yearMonth = yearMonth,
                plan = plan
            )
            viewAction = SetupPlanAction.Close
        }
    }

    fun onEditPlanClick(
        plan: Plan,
        isFromButtonClick: Boolean
    ) {
        viewModelScope.launch {
            val oldPlan = this@SetupPlanViewModel.plan ?: return@launch
            val newPlan = plan.copy(
                id = oldPlan.id,
                spentAmount = oldPlan.spentAmount
            )

            setupPlanAnalytics.trackEditClick(isFromButtonClick, oldPlan, newPlan)

            plansInteractor.updatePlan(
                yearMonth = yearMonth,
                oldPlan = oldPlan,
                newPlan = newPlan
            )
            viewAction = SetupPlanAction.Close
        }
    }

    fun onDeletePlanClick(plan: Plan, dialogKey: String) {
        setupPlanAnalytics.trackDeletePlanClick(plan)
        viewModelScope.launch {
            plansInteractor.deletePlan(plan)
            viewAction = SetupPlanAction.DismissDialog(dialogKey)
            showPreviousScreenSnackbar(
                snackbarState = SnackbarState.Information(
                    iconResId = MR.images.ic_delete,
                    textResId = MR.strings.toast_text_limit_deleted,
                    actionState = SnackbarActionState.Undo(
                        onAction = { restorePlan(plan) }
                    )
                )
            )
            viewAction = SetupPlanAction.Close
        }
    }

    private fun restorePlan(plan: Plan) {
        viewModelScope.launch {
            setupPlanAnalytics.trackRestorePlan()
            plansInteractor.addPlan(
                yearMonth = yearMonth,
                plan = plan
            )
            hideSnackbar()
        }
    }

    fun onBackClick() {
        setupPlanAnalytics.trackBackClick()
        if (hasPreviousStep.value) {
            currentFlowState.previous()
        } else {
            viewAction = SetupPlanAction.Close
        }
    }

    fun onCategorySelect(category: Category) {
        setupPlanAnalytics.trackCategorySelect(category)
        _selectedCategory.value = category
        currentFlowState.next()
    }

    fun onKeyboardButtonClick(command: KeyboardCommand) {
        val amountTextStateFlow =  _primaryAmountText
        amountTextStateFlow.update {
            amountTextStateFlow.value.applyKeyboardCommand(command)
                .takeIf {
                    it.length < MaxAmountLength &&
                            it.toDoubleOrNull() ?: 0.0 <= MaxAmountValue
                }
                ?: amountTextStateFlow.value
        }
    }

    fun onCurrentStepSelect(step: SetupPlanStep) {
        trackCurrentStepSelect(step)
        currentFlowState.setStep(step)
    }

    private fun trackCurrentStepSelect(step: SetupPlanStep) {
        when (step) {
            SetupPlanStep.Category -> {
                setupPlanAnalytics.trackCategoryClick()
            }
            SetupPlanStep.PrimaryAmount -> {
                setupPlanAnalytics.trackPrimaryAmountClick(primaryAmountText.value)
            }
        }
    }

    fun onPrimaryAmountClick() {
        onCurrentStepSelect(SetupPlanStep.PrimaryAmount)
    }
}