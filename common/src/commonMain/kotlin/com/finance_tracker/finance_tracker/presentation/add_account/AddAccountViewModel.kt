package com.finance_tracker.finance_tracker.presentation.add_account

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.core.common.EventChannel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddAccountViewModel(
    private val accountsRepository: AccountsRepository,
    private val _account: Account
): KViewModel() {

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

    private val _selectedCurrency = MutableStateFlow(
        account?.balance?.currency ?: amountCurrencies.value.first()
    )
    val selectedCurrency = _selectedCurrency.asStateFlow()

    private val _enteredAccountName = MutableStateFlow(account?.name.orEmpty())
    val enteredAccountName = _enteredAccountName.asStateFlow()

    private val _enteredAmount = MutableStateFlow(account?.balance ?: Amount.default)
    val enteredAmount = _enteredAmount.asStateFlow()

    private val _events = EventChannel<AddAccountEvent>()
    val events = _events.receiveAsFlow()

    val isAddButtonEnabled = combine(
        enteredAccountName,
        selectedType,
        selectedColor,
        enteredAmount
    ) { accountName, selectedType, selectedColor, enteredAmount ->
        accountName.isNotBlank() && selectedType != null &&
                selectedColor != null && enteredAmount.amountValue != 0.0
    }.stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    init {
        loadAccountColors()
    }

    private fun loadAccountColors() {
        viewModelScope.launch {
            _colors.value = accountsRepository.getAllAccountColors()
            _selectedColor.value = colors.value.firstOrNull { it == account?.colorModel }
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

    fun onAmountChange(amount: String) {
        _enteredAmount.value = Amount(
            currency = selectedCurrency.value,
            amountValue = amount.toDouble()
        )
    }

    fun onDeleteClick(account: Account) {
        viewModelScope.launch {
            accountsRepository.deleteAccountById(account.id)
        }
    }

    fun onAddAccountClick() {
        viewModelScope.launch {
            val accountName = enteredAccountName.value.takeIf { it.isNotBlank() } ?: run {
                _events.send(AddAccountEvent.ShowToast(
                    textId = "new_account_error_enter_account_name"
                ))
                return@launch
            }
            val selectedColorId = selectedColor.value?.id ?: run {
                _events.send(AddAccountEvent.ShowToast(
                    textId = "new_account_error_select_account_color"
                ))
                return@launch
            }
            val balance = enteredAmount.value
            val type = selectedType.value ?: run {
                _events.send(AddAccountEvent.ShowToast(
                    textId = "new_account_error_select_account_type"
                ))
                return@launch
            }
            if (account == null) {
                accountsRepository.insertAccount(
                    accountName = accountName,
                    balance = balance.amountValue,
                    colorId = selectedColorId,
                    type = type,
                    currency = selectedCurrency.value
                )
            } else {
                accountsRepository.updateAccount(
                    type = type,
                    name = accountName,
                    balance = balance.amountValue,
                    colorId = selectedColorId,
                    currency = selectedCurrency.value,
                    id = account.id,
                )
            }
            _events.send(AddAccountEvent.Close)
        }
    }
}