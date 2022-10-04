package com.finance_tracker.finance_tracker.sreens.add_trnsaction

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
import com.finance_tracker.finance_tracker.data.models.Account
import com.finance_tracker.finance_tracker.data.models.Category
import com.finance_tracker.finance_tracker.sreens.add_trnsaction.views.AddButtonSection
import com.finance_tracker.finance_tracker.sreens.add_trnsaction.views.AmountTextField
import com.finance_tracker.finance_tracker.sreens.add_trnsaction.views.CalendarDayView
import com.finance_tracker.finance_tracker.sreens.add_trnsaction.views.CategoriesAppBar
import com.finance_tracker.finance_tracker.sreens.add_trnsaction.views.EnterTransactionStep
import com.finance_tracker.finance_tracker.sreens.add_trnsaction.views.StepsEnterTransactionBar
import com.finance_tracker.finance_tracker.sreens.add_trnsaction.views.StepsEnterTransactionBarData
import com.finance_tracker.finance_tracker.sreens.add_trnsaction.views.enter_transaction_controller.EnterTransactionController
import com.finance_tracker.finance_tracker.sreens.add_trnsaction.views.enter_transaction_controller.KeyboardCommand
import com.finance_tracker.finance_tracker.theme.CoinTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@RootNavGraph
@Destination
@Composable
fun AddTransactionScreen(
    navigator: DestinationsNavigator
) {
    CoinTheme {
        Column {
            CategoriesAppBar(
                navigator = navigator
            )

            var amountText by remember { mutableStateOf("0") }
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
                    var accountData by remember {
                        mutableStateOf<Account?>(null)
                    }
                    var categoryData by remember {
                        mutableStateOf<Category?>(null)
                    }

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
                        paddingValues = WindowInsets.navigationBars.asPaddingValues()
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