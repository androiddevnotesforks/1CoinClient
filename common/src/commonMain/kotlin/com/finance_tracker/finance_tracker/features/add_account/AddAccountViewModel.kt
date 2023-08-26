package com.finance_tracker.finance_tracker.features.add_account

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.TextFieldValue
import com.finance_tracker.finance_tracker.core.common.TextRange
import com.finance_tracker.finance_tracker.core.common.formatters.evaluateDoubleWithReplace
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.common.formatters.parseToDouble
import com.finance_tracker.finance_tracker.core.common.keyboard.CalculationState
import com.finance_tracker.finance_tracker.core.common.keyboard.KeyboardAction
import com.finance_tracker.finance_tracker.core.common.keyboard.applyKeyboardAction
import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.common.view_models.hideSnackbar
import com.finance_tracker.finance_tracker.core.common.view_models.showCurrentScreenSnackbar
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarActionState
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarState
import com.finance_tracker.finance_tracker.domain.interactors.AccountsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.features.add_account.analytics.AddAccountAnalytics
import com.github.murzagalin.evaluator.Evaluator
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddAccountViewModel(
    private val accountsInteractor: AccountsInteractor,
    private val currenciesInteractor: CurrenciesInteractor,
    private val _account: Account,
    private val addAccountAnalytics: AddAccountAnalytics,
    private val evaluator: Evaluator
) : BaseViewModel<AddAccountAction>() {

    private val account: Account? = _account.takeIf { _account != Account.EMPTY }

    private val _amountCurrencies = MutableStateFlow(Currency.list)
    val amountCurrencies = _amountCurrencies.asStateFlow()

    private val _types = MutableStateFlow(Account.Type.values().toList())
    val types = _types.asStateFlow()

    private val _colors = MutableStateFlow(emptyList<AccountColorModel>())
    val colors = _colors.asStateFlow()

    private val _selectedColor = MutableStateFlow<AccountColorModel?>(null)
    val selectedColor = _selectedColor.asStateFlow()

    private val _selectedType = MutableStateFlow(account?.type)
    val selectedType = _selectedType.asStateFlow()

    private val initEditAccountCurrency = account?.balance?.currency
    private val _selectedCurrency = MutableStateFlow(initEditAccountCurrency ?: Currency.default)
    val selectedCurrency = _selectedCurrency.asStateFlow()

    private val _enteredAccountName = MutableStateFlow(account?.name.orEmpty())
    val enteredAccountName = _enteredAccountName.asStateFlow()

    private val _enteredBalance = MutableStateFlow(
        TextFieldValue(
            text = account?.balance?.amountValue?.format().orEmpty(),
            selection = TextRange.Zero,
        )
    )
    val enteredBalance = _enteredBalance.asStateFlow()

    @Suppress("SwallowedException")
    val balanceCalculationResult = enteredBalance
        .map {
            val (calculation, isError) = try {
                evaluator.evaluateDoubleWithReplace(it.text).format() to false
            } catch (exception: IllegalArgumentException) {
                "0" to enteredBalance.value.text.isNotEmpty()
            }

            CalculationState(
                calculationResult = calculation,
                isError = isError
            )
        }.stateIn(viewModelScope, initialValue = CalculationState("0", false))

    val isAddButtonEnabled = combine(
        enteredAccountName,
        selectedType,
        selectedColor
    ) { accountName, selectedType, selectedColor ->
        accountName.isNotBlank() && selectedType != null && selectedColor != null
    }.stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    init {
        loadAccountColors()
        loadPrimaryCurrency()
        addAccountAnalytics.trackScreenOpen(account)
    }

    private fun loadAccountColors() {
        viewModelScope.launch {
            _colors.value = accountsInteractor.getAllAccountColors()
            _selectedColor.value = colors.value.firstOrNull { it == account?.colorModel }
        }
    }

    private fun loadPrimaryCurrency() {
        if (initEditAccountCurrency != null) return

        viewModelScope.launch {
            _selectedCurrency.value = currenciesInteractor.getPrimaryCurrency()
        }
    }

    fun onAccountNameChange(accountName: String) {
        _enteredAccountName.value = accountName
    }

    fun onAccountTypeSelect(accountType: Account.Type) {
        _selectedType.value = accountType
    }

    fun onCurrencySelect(currency: Currency) {
        _selectedCurrency.value = currency
    }

    fun onAccountColorSelect(accountColor: AccountColorModel) {
        _selectedColor.value = accountColor
    }

    fun onAmountChange(amount: TextFieldValue) {
        _enteredBalance.value = amount
    }

    fun onKeyboardButtonClick(keyboardAction: KeyboardAction) {
        _enteredBalance.applyKeyboardAction(keyboardAction)
    }

    fun onConfirmDeletingClick(account: Account, dialogKey: String) {
        addAccountAnalytics.trackConfirmDeletingClick(account)
        viewAction = AddAccountAction.DismissDeleteDialog(dialogKey)
        viewModelScope.launch {
            accountsInteractor.deleteAccountById(account.id)
            viewAction = AddAccountAction.Close
        }
    }

    fun onAddAccountClick() {
        trackAddAccountClick()

        viewModelScope.launch {
            val accountName = enteredAccountName.value.takeIf { it.isNotBlank() } ?: run {
                showSnackbar(MR.strings.new_account_error_enter_account_name)
                return@launch
            }
            val selectedColorId = selectedColor.value?.id ?: run {
                showSnackbar(MR.strings.new_account_error_select_account_color)
                return@launch
            }
            val type = selectedType.value ?: run {
                showSnackbar(MR.strings.new_account_error_select_account_type)
                return@launch
            }
            val balance = balanceCalculationResult
                .value
                .takeIf { !it.isError }
                ?.calculationResult
                ?.parseToDouble() ?: run {
                showSnackbar(MR.strings.new_account_error_enter_account_balance)
                return@launch
            }
            if (account == null) {
                accountsInteractor.insertAccount(
                    accountName = accountName,
                    type = type,
                    colorId = selectedColorId,
                    currency = selectedCurrency.value,
                    balance = balance
                )
            } else {
                accountsInteractor.updateAccount(
                    id = account.id,
                    name = accountName,
                    type = type,
                    colorId = selectedColorId,
                    currency = selectedCurrency.value,
                    balance = balance,
                )
            }
            viewAction = AddAccountAction.Close
        }
    }

    private fun showSnackbar(textResId: StringResource) {
        viewAction = AddAccountAction.HideKeyboard
        showCurrentScreenSnackbar(
            snackbarState = SnackbarState.Error(
                textResId = textResId,
                actionState = SnackbarActionState.Close(onAction = ::hideSnackbar)
            )
        )
    }

    private fun trackAddAccountClick() {
        if (account == null) {
            addAccountAnalytics.trackAddClick(
                accountName = enteredAccountName.value.takeIf { it.isNotBlank() },
                accountType = selectedType.value,
                colorModel = selectedColor.value?.id?.let { AccountColorModel.from(it) }
            )
        } else {
            addAccountAnalytics.trackSaveClick(
                accountName = enteredAccountName.value.takeIf { it.isNotBlank() },
                accountType = selectedType.value,
                colorModel = selectedColor.value?.id?.let { AccountColorModel.from(it) }
            )
        }
    }

    fun onBackClick() {
        addAccountAnalytics.trackBackClick()
        viewAction = AddAccountAction.Close
    }

    fun onDeleteClick() {
        account?.let {
            addAccountAnalytics.trackDeleteClick(account)
            viewAction = AddAccountAction.ShowDeleteDialog(account)
        }
    }

    fun onCancelDeletingClick(account: Account, dialogKey: String) {
        addAccountAnalytics.trackCancelDeletingClick(account)
        viewAction = AddAccountAction.DismissDeleteDialog(dialogKey)
    }
}