package com.finance_tracker.finance_tracker.features.home

import com.finance_tracker.finance_tracker.core.common.convertToCurrencyValue
import com.finance_tracker.finance_tracker.core.common.getKoin
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypesMode
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.interactors.AccountsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.DashboardSettingsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.transactions.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.features.analytics.delegates.AnalyticsDelegates
import com.finance_tracker.finance_tracker.features.analytics.delegates.MonthTxsByCategoryDelegate
import com.finance_tracker.finance_tracker.features.analytics.delegates.TrendsAnalyticsDelegate
import com.finance_tracker.finance_tracker.features.home.analytics.HomeAnalytics
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class HomeViewModel(
    private val accountsInteractor: AccountsInteractor,
    private val currenciesInteractor: CurrenciesInteractor,
    private val transactionsInteractor: TransactionsInteractor,
    private val homeAnalytics: HomeAnalytics,
    dashboardSettingsInteractor: DashboardSettingsInteractor
): BaseViewModel<HomeAction>() {
    private val transactionTypes = TransactionTypesMode.Main.types
    private val typesToAnalyticsDelegates = transactionTypes.associateWith {
        getKoin().get<AnalyticsDelegates>().apply {
            setTransactionType(it.toTransactionType())
        }
    }
    private val incomeAnalyticsDelegates by lazy {
        typesToAnalyticsDelegates[TransactionTypeTab.Income]
            ?: error("No AnalyticsDelegates for Income")
    }
    private val expenseAnalyticsDelegates by lazy {
        typesToAnalyticsDelegates[TransactionTypeTab.Expense]
            ?: error("No AnalyticsDelegates for Expense")
    }
    val incomeMonthTxsByCategoryDelegate: MonthTxsByCategoryDelegate
        get() = incomeAnalyticsDelegates.monthTxsByCategoryDelegate
    val expenseMonthTxsByCategoryDelegate: MonthTxsByCategoryDelegate
        get() = expenseAnalyticsDelegates.monthTxsByCategoryDelegate
    val incomeTrendsAnalyticsDelegate: TrendsAnalyticsDelegate
        get() = incomeAnalyticsDelegates.trendsAnalyticsDelegate
    val expenseTrendsAnalyticsDelegate: TrendsAnalyticsDelegate
        get() = expenseAnalyticsDelegates.trendsAnalyticsDelegate

    private val currencyRatesFlow = currenciesInteractor.getCurrencyRatesFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = mapOf())

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    val widgets = dashboardSettingsInteractor.getActiveDashboardWidgets()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList())

    val lastTransactions = transactionsInteractor.getLastTransactions()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList())

    private val _totalBalance = MutableStateFlow(Amount.default)
    val totalBalance = _totalBalance.asStateFlow()

    private var loadTotalAmountJob: Job? = null

    private val totalCurrencyFlow = currenciesInteractor.getPrimaryCurrencyFlow()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = Currency.default)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Napier.e("HomeViewModel", throwable)
    }

    init {
        updateCurrencyRates()
        homeAnalytics.trackScreenOpen()
    }

    fun onScreenComposed() {
        loadAccounts()
        loadTotalAmount()
        updateMonthTxsByCategory()
    }

    private fun updateMonthTxsByCategory() {
        typesToAnalyticsDelegates.values.forEach { it.updateMonthTxsByCategory() }
    }

    private fun updateCurrencyRates() {
        viewModelScope.launch {
            currenciesInteractor.updateCurrencyRates()
        }
    }

    private fun loadAccounts() {
        val oldAccountsCount = _accounts.value.size
        viewModelScope.launch {
            _accounts.value  = accountsInteractor.getAllAccountsFromDatabase()
            val newAccountsCount = _accounts.value.size
            if (oldAccountsCount in 1 until newAccountsCount) {
                viewAction = HomeAction.ScrollToItemAccounts(newAccountsCount - 1)
            }
        }
    }

    private fun loadTotalAmount() {
        loadTotalAmountJob?.cancel()
        loadTotalAmountJob = currencyRatesFlow.combine(totalCurrencyFlow) {
                currencyRates, baseCurrency ->
            val balanceInBaseCurrency = getTotalBalanceAmountInCurrency(
                currencyRates = currencyRates,
                currency = baseCurrency
            )
            _totalBalance.value = Amount(
                currency = baseCurrency,
                amountValue = balanceInBaseCurrency
            )
        }
            .launchIn(viewModelScope + exceptionHandler)
    }

    private suspend fun getTotalBalanceAmountInCurrency(
        currencyRates: CurrencyRates,
        currency: Currency
    ): Double {
        return accountsInteractor.getAllAccountsFromDatabase()
            .sumOf { account ->
                account.balance.convertToCurrencyValue(
                    toCurrency = currency,
                    currencyRates = currencyRates
                )
            }
    }

    fun onMyAccountsClick() {
        homeAnalytics.trackMyAccountsClick()
        viewAction = HomeAction.OpenAccountsScreen
    }

    fun onAccountClick(account: Account) {
        homeAnalytics.trackAccountClick(account)
        viewAction = HomeAction.OpenAccountDetailScreen(account)
    }

    fun onAddAccountClick() {
        homeAnalytics.trackAddAccountClick()
        viewAction = HomeAction.OpenAddAccountScreen
    }

    fun onLastTransactionsClick() {
        homeAnalytics.trackLastTransactionsClick()
        viewAction = HomeAction.OpenTransactionsScreen
    }

    fun onTransactionClick(transaction: Transaction) {
        homeAnalytics.trackTransactionClick(transaction)
        viewAction = HomeAction.OpenEditTransactionScreen(transaction)
    }

    fun onSettingsClick() {
        homeAnalytics.trackSettingsClick()
        viewAction = HomeAction.ShowSettingsDialog
    }

    override fun onCleared() {
        super.onCleared()
        typesToAnalyticsDelegates.values.forEach { it.cancel() }
    }
}