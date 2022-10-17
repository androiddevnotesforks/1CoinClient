package com.finance_tracker.finance_tracker.presentation.add_transaction

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.AddButtonSection
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.AmountTextField
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.CalendarDayView
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.CategoriesAppBar
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.EnterTransactionStep
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.StepsEnterTransactionBar
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.StepsEnterTransactionBarData
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.enter_transaction_controller.EnterTransactionController
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.enter_transaction_controller.KeyboardCommand
import com.finance_tracker.finance_tracker.theme.CoinTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.koin.androidx.compose.getViewModel
import java.util.Date

@RootNavGraph
@Destination
@Composable
fun AddTransactionScreen(
    navigator: DestinationsNavigator,
    viewModel: AddTransactionViewModel = getViewModel()
) {
    CoinTheme {
        Column {
            CategoriesAppBar(
                navigator = navigator
            )

            var amountText by remember { mutableStateOf("0") }
            var accountData by remember {
                mutableStateOf<Account?>(null)
            }
            var categoryData by remember {
                mutableStateOf<Category?>(null)
            }
            AmountTextField(
                modifier = Modifier
                    .weight(1f),
                currency = "$",
                amount = amountText.toDouble()
            )

            CalendarDayView(
                modifier = Modifier
            )

            Surface(
                modifier = Modifier.weight(2f),
                elevation = 8.dp
            ) {

                val steps = EnterTransactionStep.values()
                val firstStep = steps.first()
                var currentStep by remember { mutableStateOf(firstStep) }
                var previousStepIndex by remember { mutableStateOf(-1) }
                BackHandler {
                    if (currentStep != firstStep) {
                        currentStep = currentStep.previous()
                    } else {
                        navigator.popBackStack()
                    }
                }
                LaunchedEffect(currentStep) {
                    previousStepIndex = currentStep.ordinal
                }

                Column {
                    StepsEnterTransactionBar(
                        data = StepsEnterTransactionBarData(
                            currentStep = currentStep,
                            accountData = accountData,
                            categoryData = categoryData
                        ),
                        onStepSelect = { currentStep = it }
                    )

                    EnterTransactionController(
                        modifier = Modifier.weight(1f),
                        accounts = viewModel.accounts.value,
                        categories = viewModel.categories.value,
                        currentStep = currentStep,
                        animationDirection = if (currentStep.ordinal >= previousStepIndex) {
                            1
                        } else {
                            -1
                        },
                        onAccountSelect = {
                            accountData = it
                            currentStep = currentStep.next()
                        },
                        onCategorySelect = {
                            categoryData = it
                            currentStep = currentStep.next()
                        },
                        onKeyboardButtonClick = { command ->
                            when (command) {
                                KeyboardCommand.Delete -> {
                                    when {
                                        amountText.length <= 1 && amountText.toDouble() == 0.0 -> {
                                            /* ignore */
                                        }
                                        amountText.length <= 1 && amountText.toDouble() != 0.0 -> {
                                            amountText = "0"
                                        }
                                        amountText.length > 1 && amountText[amountText.length - 2] == '.' -> {
                                            amountText = amountText.dropLast(2)
                                        }
                                        else -> {
                                            amountText = amountText.dropLast(1)
                                        }
                                    }
                                }
                                is KeyboardCommand.Digit -> {
                                    amountText += command.value.toString()
                                }
                                KeyboardCommand.Point -> {
                                    if (!amountText.contains(".")) {
                                        amountText += "."
                                    }
                                }
                            }
                        }
                    )

                    AddButtonSection(
                        paddingValues = WindowInsets.navigationBars.asPaddingValues(),
                        onAddClick = {
                            viewModel.addTransaction(
                                Transaction(
                                    type = TransactionType.Expense,
                                    amountCurrency = "$",
                                    account = accountData ?: return@AddButtonSection,
                                    category = categoryData,
                                    amount = amountText.toDouble(),
                                    date = Date()
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AddTransactionScreenPreview() {
    AddTransactionScreen(
        navigator = EmptyDestinationsNavigator
    )
}