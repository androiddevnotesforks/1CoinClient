package com.finance_tracker.finance_tracker.features.add_transaction

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.TextFieldValue
import com.finance_tracker.finance_tracker.core.common.TextRange
import com.finance_tracker.finance_tracker.core.common.combineExtended
import com.finance_tracker.finance_tracker.core.common.date.currentLocalDate
import com.finance_tracker.finance_tracker.core.common.date.currentLocalDateTime
import com.finance_tracker.finance_tracker.core.common.formatters.evaluateDoubleWithReplace
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.common.formatters.parseToDouble
import com.finance_tracker.finance_tracker.core.common.keyboard.CalculationState
import com.finance_tracker.finance_tracker.core.common.keyboard.KeyboardAction
import com.finance_tracker.finance_tracker.core.common.keyboard.applyKeyboardAction
import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.core.common.toDateTime
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.hideSnackbar
import com.finance_tracker.finance_tracker.core.common.view_models.showPreviousScreenSnackbar
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarActionState
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarState
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionTypeTab
import com.finance_tracker.finance_tracker.data.database.mappers.accountToDomainModel
import com.finance_tracker.finance_tracker.data.database.mappers.categoryToDomainModel
import com.finance_tracker.finance_tracker.domain.interactors.transactions.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.features.add_transaction.analytics.AddTransactionAnalytics
import com.financetracker.financetracker.data.AccountsEntityQueries
import com.financetracker.financetracker.data.CategoriesEntityQueries
import com.github.murzagalin.evaluator.Evaluator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

private const val MaxAmountValue = 100_000_000_000_000.0
private const val MaxAmountLength = 18

@Suppress("LargeClass")
class AddTransactionViewModel(
    private val transactionsInteractor: TransactionsInteractor,
    private val accountsEntityQueries: AccountsEntityQueries,
    private val categoriesEntityQueries: CategoriesEntityQueries,
    params: AddTransactionScreenParams,
    private val addTransactionAnalytics: AddTransactionAnalytics,
    private val evaluator: Evaluator
) : BaseViewModel<AddTransactionAction>() {

    private val preselectedAccount: Account? = params.preselectedAccount
    private val transaction: Transaction? = params.transaction

    val isEditMode = transaction != null

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _selectedPrimaryAccount: MutableStateFlow<Account?> = MutableStateFlow(
        value = transaction?.primaryAccount ?: params.preselectedAccount
    )
    val selectedPrimaryAccount: StateFlow<Account?> = _selectedPrimaryAccount.asStateFlow()

    private val _selectedSecondaryAccount: MutableStateFlow<Account?> = MutableStateFlow(
        value = transaction?.secondaryAccount
    )
    val selectedSecondaryAccount: StateFlow<Account?> = _selectedSecondaryAccount.asStateFlow()

    private val transactionInsertionDate = transaction?.insertionDateTime
    val primaryCurrency = selectedPrimaryAccount
        .map { it?.balance?.currency }
        .stateIn(viewModelScope, initialValue = null)

    val secondaryCurrency = selectedSecondaryAccount
        .map { it?.balance?.currency }
        .stateIn(viewModelScope, initialValue = null)

    private val selectedIncomeCategory = MutableStateFlow(
        transaction?._category?.takeIf { it.isIncome }
    )
    private val selectedExpenseCategory = MutableStateFlow(
        transaction?._category?.takeIf { it.isExpense }
    )

    private val initialSelectedDate = transaction?.dateTime?.date ?: Clock.System.currentLocalDate()
    private val _selectedDate: MutableStateFlow<LocalDate> = MutableStateFlow(initialSelectedDate)
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val initialPrimaryAmountValue = transaction?.primaryAmount?.amountValue?.format()

    private val _primaryAmountFormula = MutableStateFlow(
        TextFieldValue(
            text = initialPrimaryAmountValue ?: "0",
            selection = TextRange(
                start = initialPrimaryAmountValue?.length ?: 1,
                end = initialPrimaryAmountValue?.length ?: 1
            ),
        )
    )
    val primaryAmountFormula = _primaryAmountFormula.asStateFlow()

    private val primaryAmountCalculation = _primaryAmountFormula
        .map { calculateAmountFormula(it.text) }
        .stateIn(viewModelScope, initialValue = CalculationState("0", false))

    private val initialSecondaryAmountValue = transaction?.secondaryAmount?.amountValue?.format()

    private val _secondaryAmountFormula = MutableStateFlow(
        TextFieldValue(
            text = initialSecondaryAmountValue ?: "0",
            selection = TextRange(
                start = initialSecondaryAmountValue?.length ?: 1,
                end = initialSecondaryAmountValue?.length ?: 1
            ),
        )
    )
    val secondaryAmountFormula = _secondaryAmountFormula.asStateFlow()

    private val secondaryAmountCalculation = _secondaryAmountFormula
        .map { calculateAmountFormula(it.text) }
        .stateIn(viewModelScope, initialValue = CalculationState("0", false))

    private val _expenseCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val expenseCategories: StateFlow<List<Category>> = _expenseCategories.asStateFlow()

    private val _incomeCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val incomeCategories: StateFlow<List<Category>> = _incomeCategories.asStateFlow()

    private val initialSelectedTransactionType = transaction?.type?.toTransactionTypeTab() ?: TransactionTypeTab.Expense
    private val _selectedTransactionType: MutableStateFlow<TransactionTypeTab> =
        MutableStateFlow(initialSelectedTransactionType)
    val selectedTransactionType: StateFlow<TransactionTypeTab> = _selectedTransactionType.asStateFlow()

    private val flowStates = AddTransactionFlowState.createNewStatesForAllFlow()
    private val defaultFlow = AddTransactionFlow.Expense
    private val currentFlowState = selectedTransactionType
        .map {
            flowStates[it.toAddTransactionFlow()]
                ?: error("No FlowState for transactionTypeTab $it")
        }
        .stateIn(viewModelScope, initialValue = AddTransactionFlowState(defaultFlow))
    val currentFlow = currentFlowState
        .map { it.flow }
        .stateIn(viewModelScope, initialValue = defaultFlow)

    val selectedCategory = combine(
        currentFlow,
        selectedIncomeCategory,
        selectedExpenseCategory
    ) { currentFlow, selectedIncomeCategory, selectedExpenseCategory ->
        when (currentFlow) {
            AddTransactionFlow.Expense -> selectedExpenseCategory
            AddTransactionFlow.Income -> selectedIncomeCategory
            AddTransactionFlow.Transfer -> null
        }
    }.stateIn(viewModelScope, initialValue = null)

    val currentStep = MutableStateFlow<EnterTransactionStep?>(null)
    private val hasPreviousStep = MutableStateFlow(false)

    @Suppress("UnnecessaryParentheses")
    val isAddTransactionEnabled = combineExtended(
        currentFlow, selectedPrimaryAccount, selectedSecondaryAccount,
        selectedCategory, primaryAmountFormula, secondaryAmountFormula
    ) { currentFlow, selectedPrimaryAccount, selectedSecondaryAccount,
        selectedCategory, primaryAmountFormula, secondaryAmountFormula ->

        when (currentFlow) {
            AddTransactionFlow.Expense,
            AddTransactionFlow.Income -> {
                selectedPrimaryAccount != null &&
                        selectedCategory != null &&
                        primaryAmountFormula.text.isNotEmpty() &&
                        primaryAmountFormula.text != "0"
            }

            AddTransactionFlow.Transfer -> {
                selectedPrimaryAccount != null &&
                        selectedSecondaryAccount != null &&
                        primaryAmountFormula.text.isNotEmpty() &&
                        primaryAmountFormula.text != "0" &&
                        secondaryAmountFormula.text.isNotEmpty() &&
                        secondaryAmountFormula.text != "0"
            }
        }
    }.stateIn(viewModelScope, initialValue = false)

    init {
        addTransactionAnalytics.trackAddTransactionScreenOpen()
        observeCurrentFlowState()
        initCurrentStep()
        observeAmountCalculations()
    }

    private fun observeAmountCalculations() {
        primaryAmountFormula
            .onEach {
                if (
                    primaryCurrency.value == secondaryCurrency.value &&
                    primaryCurrency.value != null
                ) {
                    _secondaryAmountFormula.value = it
                }
            }
            .launchIn(viewModelScope)

        primaryAmountCalculation.launchIn(viewModelScope)
        secondaryAmountCalculation.launchIn(viewModelScope)
    }

    private fun observeCurrentFlowState() {
        viewModelScope.launch {
            currentFlowState
                .onEach { currentFlowState ->
                    observeHasPreviousStepFlow(currentFlowState, this)
                    observeCurrentStepFlow(currentFlowState, this)

                    if (isEditMode && currentStep.value == null) return@onEach

                    currentFlowState.setCurrentStepForFlowState(
                        isPrimaryAccountEmpty = selectedPrimaryAccount.value == null,
                        isSecondaryAccountEmpty = selectedSecondaryAccount.value == null,
                        isExpenseCategoryEmpty = selectedExpenseCategory.value == null,
                        isIncomeCategoryEmpty = selectedIncomeCategory.value == null
                    )
                }
                .launchIn(this)
        }
    }

    private fun observeHasPreviousStepFlow(
        currentFlowState: AddTransactionFlowState,
        scope: CoroutineScope
    ) {
        currentFlowState.hasPreviousStepFlow
            .onEach { hasPreviousStep.value = it }
            .launchIn(scope)
    }

    private fun observeCurrentStepFlow(
        currentFlowState: AddTransactionFlowState,
        scope: CoroutineScope
    ) {
        currentFlowState.currentStepFlow
            .onEach {
                currentStep.value = it
            }
            .launchIn(scope)
    }

    private fun initCurrentStep() {
        if (preselectedAccount != null) {
            currentFlowState.value.setStepAfter(EnterTransactionStep.PrimaryAccount)
        }
    }

    fun onScreenComposed() {
        loadAccounts()
        loadCategories()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _accounts.value = accountsEntityQueries.getAllAccounts().executeAsList()
                .map { it.accountToDomainModel() }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _expenseCategories.value = categoriesEntityQueries.getAllExpenseCategories().executeAsList()
                .map { it.categoryToDomainModel() }
            _incomeCategories.value = categoriesEntityQueries.getAllIncomeCategories().executeAsList()
                .map { it.categoryToDomainModel() }
        }
    }

    @Suppress("CyclomaticComplexMethod")
    fun addOrUpdateTransaction(fromButtonClick: Boolean) {
        val primaryAmountCalcResult = primaryAmountCalculation.value.calculationResult.parseToDouble() ?: 0.0
        val secondaryAmountCalcResult = secondaryAmountCalculation.value.calculationResult.parseToDouble() ?: 0.0
        val isCalcIsFailed = primaryAmountCalculation.value.isError || secondaryAmountCalculation.value.isError

        if (isCalcIsFailed) {
            viewAction = AddTransactionAction.DisplayWrongCalculationDialog

            return
        }

        if (primaryAmountCalcResult < 0.0 || secondaryAmountCalcResult < 0.0) {
            viewAction = AddTransactionAction.DisplayNegativeAmountDialog

            return
        }

        val primaryAccount = selectedPrimaryAccount.value
        val primaryCurrency = primaryCurrency.value
        val isEdit = isEditMode

        val insertionDateTime = if (isEdit) {
            transactionInsertionDate
        } else {
            Clock.System.currentLocalDateTime()
        }

        var transaction: Transaction? = null
        if (currentFlow.value == AddTransactionFlow.Transfer) {
            val secondaryAccount = selectedSecondaryAccount.value
            val secondaryCurrency = secondaryCurrency.value

            @Suppress("ComplexCondition")
            if (
                primaryAccount != null &&
                secondaryAccount != null &&
                primaryCurrency != null &&
                secondaryCurrency != null
            ) {
                transaction = Transaction(
                    type = selectedTransactionType.value.toTransactionType(),
                    primaryAccount = primaryAccount,
                    _category = null,
                    primaryAmount = Amount(
                        currency = primaryCurrency,
                        amountValue = primaryAmountCalcResult
                    ),
                    secondaryAmount = Amount(
                        currency = secondaryCurrency,
                        amountValue = secondaryAmountCalcResult
                    ),
                    secondaryAccount = secondaryAccount,
                    dateTime = selectedDate.value.toDateTime(),
                    insertionDateTime = insertionDateTime
                )
            }
        } else {
            if (primaryAccount != null && primaryCurrency != null) {
                transaction = Transaction(
                    type = selectedTransactionType.value.toTransactionType(),
                    primaryAccount = primaryAccount,
                    _category = selectedCategory.value,
                    primaryAmount = Amount(
                        currency = primaryCurrency,
                        amountValue = primaryAmountCalcResult
                    ),
                    dateTime = selectedDate.value.toDateTime(),
                    insertionDateTime = insertionDateTime
                )
            }
        }

        if (transaction != null) {
            if (isEdit) {
                onEditTransactionClick(
                    transaction = transaction,
                    isFromButtonClick = fromButtonClick
                )
            } else {
                onAddTransactionClick(
                    transaction = transaction,
                    isFromButtonClick = fromButtonClick
                )
            }
        }
    }

    private fun onAddTransactionClick(
        transaction: Transaction,
        isFromButtonClick: Boolean
    ) {
        addTransactionAnalytics.trackAddClick(isFromButtonClick, transaction)
        viewModelScope.launch {
            transactionsInteractor.addTransaction(transaction)
            viewAction = AddTransactionAction.Close
        }
    }

    private fun onEditTransactionClick(
        transaction: Transaction,
        isFromButtonClick: Boolean
    ) {
        viewModelScope.launch {
            val oldTransaction = this@AddTransactionViewModel.transaction ?: return@launch
            val newTransaction = transaction.copy(id = oldTransaction.id)

            addTransactionAnalytics.trackEditClick(isFromButtonClick, oldTransaction, newTransaction)

            transactionsInteractor.updateTransaction(
                oldTransaction = oldTransaction,
                newTransaction = newTransaction
            )
            viewAction = AddTransactionAction.Close
        }
    }

    fun displayDeleteTransactionDialog(transaction: Transaction?) {
        viewAction = AddTransactionAction.DisplayDeleteTransactionDialog(transaction)
    }

    fun onDeleteTransactionClick(transaction: Transaction, dialogKey: String) {
        addTransactionAnalytics.trackDeleteTransactionClick(transaction)
        viewModelScope.launch {
            transactionsInteractor.deleteTransaction(transaction)
            viewAction = AddTransactionAction.DismissDialog(dialogKey)
            showPreviousScreenSnackbar(
                snackbarState = SnackbarState.Information(
                    iconResId = MR.images.ic_delete,
                    textResId = MR.strings.toast_text_transaction_deleted,
                    actionState = SnackbarActionState.Undo(
                        onAction = { restoreTransaction(transaction) }
                    )
                )
            )
            viewAction = AddTransactionAction.Close
        }
    }

    fun onBackClick() {
        val flowState = currentFlowState.value

        if (hasPreviousStep.value) {
            flowState.previous()
        } else {
            viewAction = AddTransactionAction.Close
        }
    }

    fun onDuplicateTransactionClick(transaction: Transaction?) {
        if (transaction == null) return

        addTransactionAnalytics.trackDuplicateTransactionClick(transaction)
        viewModelScope.launch {
            transactionsInteractor.addTransaction(
                transaction = transaction.copy(id = null, insertionDateTime = Clock.System.currentLocalDateTime())
            )
            viewAction = AddTransactionAction.Close
        }
    }

    fun onAccountSelect(account: Account) {
        addTransactionAnalytics.trackAccountSelect(account)
        when (currentStep.value) {
            EnterTransactionStep.PrimaryAccount -> {
                _selectedPrimaryAccount.value = account
            }

            EnterTransactionStep.SecondaryAccount -> {
                _selectedSecondaryAccount.value = account
            }

            else -> {
            }
        }
        currentFlowState.value.next()
    }

    fun onCategorySelect(category: Category) {
        addTransactionAnalytics.trackCategorySelect(category)
        when (currentFlow.value) {
            AddTransactionFlow.Expense -> {
                selectedExpenseCategory.value = category
            }

            AddTransactionFlow.Income -> {
                selectedIncomeCategory.value = category
            }

            else -> {
            }
        }
        currentFlowState.value.next()
    }

    fun onDateSelect(date: LocalDate) {
        addTransactionAnalytics.trackDateSelect(date)
        _selectedDate.value = date
    }

    fun onCalendarClick() {
        addTransactionAnalytics.trackCalendarClick()
    }

    fun onKeyboardButtonClick(keyboardAction: KeyboardAction) {
        val amountFormulaFlow = if (currentStep.value == EnterTransactionStep.SecondaryAmount) {
            _secondaryAmountFormula
        } else {
            _primaryAmountFormula
        }

        amountFormulaFlow.update { textField: TextFieldValue ->
            textField
                .applyKeyboardAction(hasInitialZero = true, keyboardAction = keyboardAction)
                .takeIf {
                    it.text.length < MaxAmountLength &&
                            it.text.parseToDouble() ?: 0.0 <= MaxAmountValue
                } ?: textField
        }
    }

    @Suppress("SwallowedException")
    private fun calculateAmountFormula(formula: String) =
        try {
            CalculationState(evaluator.evaluateDoubleWithReplace(formula).format(), false)
        } catch (exception: IllegalArgumentException) {
            CalculationState("0", formula.isNotEmpty())
        }

    private fun restoreTransaction(transaction: Transaction) {
        viewModelScope.launch {
            addTransactionAnalytics.trackRestoreTransaction()
            transactionsInteractor.addTransaction(transaction)
            hideSnackbar()
        }
    }

    fun onTransactionTypeSelect(transactionTypeTab: TransactionTypeTab) {
        addTransactionAnalytics.trackTransactionTypeSelect(
            transactionType = transactionTypeTab.toTransactionType()
        )
        _selectedTransactionType.value = transactionTypeTab
    }

    fun onAccountAdd() {
        addTransactionAnalytics.trackAddAccountClick()
        viewAction = AddTransactionAction.OpenAddAccountScreen
    }

    fun onCategoryAdd() {
        val transactionTypeTab = selectedTransactionType.value
        addTransactionAnalytics.trackAddCategoryClick(
            transactionType = transactionTypeTab.toTransactionType()
        )
        viewAction = AddTransactionAction.OpenAddCategoryScreen(
            type = transactionTypeTab
        )
    }

    fun onCurrentStepSelect(step: EnterTransactionStep) {
        trackCurrentStepSelect(step)
        currentFlowState.value.setStep(step)
    }

    private fun trackCurrentStepSelect(step: EnterTransactionStep) {
        when (step) {
            EnterTransactionStep.PrimaryAccount -> {
                addTransactionAnalytics.trackPrimaryAccountClick()
            }

            EnterTransactionStep.Category -> {
                addTransactionAnalytics.trackCategoryClick()
            }

            EnterTransactionStep.PrimaryAmount -> {
                addTransactionAnalytics.trackPrimaryAmountClick(primaryAmountFormula.value.text)
            }

            EnterTransactionStep.SecondaryAccount -> {
                addTransactionAnalytics.trackSecondaryAccountClick()
            }

            EnterTransactionStep.SecondaryAmount -> {
                addTransactionAnalytics.trackSecondaryAmountClick(secondaryAmountFormula.value.text)
            }
        }
    }

    fun onPrimaryAmountClick() {
        onCurrentStepSelect(EnterTransactionStep.PrimaryAmount)
    }

    fun onSecondaryAmountClick() {
        onCurrentStepSelect(EnterTransactionStep.SecondaryAmount)
    }

    private fun TransactionTypeTab.toAddTransactionFlow(): AddTransactionFlow {
        return when (this) {
            TransactionTypeTab.Expense -> AddTransactionFlow.Expense
            TransactionTypeTab.Income -> AddTransactionFlow.Income
            TransactionTypeTab.Transfer -> AddTransactionFlow.Transfer
        }
    }
}