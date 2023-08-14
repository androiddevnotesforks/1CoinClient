package com.finance_tracker.finance_tracker.features.plans.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.BackHandler
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.asSp
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.core.ui.dialogs.DeleteAlertDialog
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Plan
import com.finance_tracker.finance_tracker.features.add_transaction.views.LabeledAmountTextField
import com.finance_tracker.finance_tracker.features.plans.setup.views.ActionButtonsSection
import com.finance_tracker.finance_tracker.features.plans.setup.views.ExpenseLimitTopBar
import com.finance_tracker.finance_tracker.features.plans.setup.views.SetupPlanController
import com.finance_tracker.finance_tracker.features.plans.setup.views.StepsSetupPlanBar
import com.finance_tracker.finance_tracker.features.plans.setup.views.StepsSetupPlanBarData
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.parameter.parametersOf
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun SetupPlanScreen(
    params: SetupPlanScreenParams
) {
    ComposeScreen<SetupPlanViewModel>(
        parameters = { parametersOf(params) }
    ) { viewModel ->
        val navController = LocalRootController.current.findRootController()
        val modalNavController = navController.findModalController()
        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage)
        }

        val currentStep by viewModel.currentStep.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize()
                .background(CoinTheme.color.background)
        ) {
            val primaryAmountText by viewModel.primaryAmountText.collectAsState()
            val categoryData by viewModel.selectedCategory.collectAsState()
            val primaryCurrencyData by viewModel.primaryCurrency.collectAsState()
            val primaryAmountDouble = primaryAmountText.toDoubleOrNull() ?: 0.0
            val currentFlow = viewModel.currentFlow
            val isAddTransactionEnabled by viewModel.isAddTransactionEnabled.collectAsState()
            val onUpdateTransaction = { fromButtonClick: Boolean ->
                val primaryCurrency = primaryCurrencyData
                val category = categoryData
                val isEdit = viewModel.isEditMode

                var plan: Plan? = null
                if (category != null) {
                    plan = Plan(
                        category = category,
                        spentAmount = Amount.default,
                        limitAmount = Amount(
                            currency = primaryCurrency,
                            amountValue = primaryAmountDouble
                        )
                    )
                }

                if (plan != null) {
                    if (isEdit) {
                        viewModel.onEditPlanClick(
                            plan = plan,
                            isFromButtonClick = fromButtonClick
                        )
                    } else {
                        viewModel.onAddPlanClick(
                            plan = plan,
                            isFromButtonClick = fromButtonClick
                        )
                    }
                }
            }

            ExpenseLimitTopBar()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LabeledAmountTextField(
                    currency = primaryCurrencyData,
                    amount = primaryAmountText,
                    amountFontSize = 42.dp.asSp(),
                    active = currentStep == SetupPlanStep.PrimaryAmount,
                    label = stringResource(MR.strings.setup_plans_amount_hint),
                    onClick = viewModel::onPrimaryAmountClick
                )
            }

            Surface(
                modifier = Modifier
                    .`if`(currentStep != null) {
                        weight(2f)
                    },
                elevation = 8.dp
            ) {
                var previousStepIndex by rememberSaveable {
                    mutableStateOf(currentStep?.let { it.ordinal - 1 })
                }
                BackHandler {
                    viewModel.onBackClick()
                }
                LaunchedEffect(currentStep) {
                    previousStepIndex = currentStep?.ordinal
                }

                CompositionLocalProvider(LocalContentColor provides CoinTheme.color.content) {
                    Column {
                        if (isSystemInDarkTheme()) {
                            Divider(
                                thickness = 0.5.dp,
                                color = CoinTheme.color.dividers
                            )
                        }

                        StepsSetupPlanBar(
                            data = StepsSetupPlanBarData(
                                steps = currentFlow,
                                currentStep = currentStep,
                                categoryData = categoryData
                            ),
                            onStepSelect = viewModel::onCurrentStepSelect
                        )

                        val categories by viewModel.expenseCategories.collectAsState()

                        SetupPlanController(
                            modifier = Modifier
                                .`if`(currentStep == null) {
                                    height(0.dp)
                                }
                                .`if`(currentStep != null) {
                                    weight(1f)
                                },
                            categories = categories,
                            currentStep = currentStep,
                            animationDirection = when {
                                currentStep == null || previousStepIndex == null -> 0
                                currentStep!!.ordinal >= previousStepIndex!! -> 1
                                else -> -1
                            },
                            onCategorySelect = viewModel::onCategorySelect,
                            onKeyboardButtonClick = viewModel::onKeyboardButtonClick
                        )

                        ActionButtonsSection(
                            hasActiveStep = currentStep != null,
                            enabled = isAddTransactionEnabled,
                            onAddClick = {
                                val fromButtonClick = true
                                onUpdateTransaction(fromButtonClick)
                            },
                            onEditClick = {
                                val fromButtonClick = true
                                onUpdateTransaction(fromButtonClick)
                            },
                            isEditMode = viewModel.isEditMode,
                            onDeleteClick = {
                                modalNavController.present(DialogConfigurations.alert) { dialogKey ->
                                    DeletePlanDialog(
                                        key = dialogKey,
                                        plan = params.plan,
                                        modalNavController = modalNavController,
                                        onDeleteTransactionClick = { plan ->
                                            viewModel.onDeletePlanClick(
                                                plan,
                                                dialogKey
                                            )
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeletePlanDialog(
    key: String,
    plan: Plan?,
    modalNavController: ModalController,
    onDeleteTransactionClick: (Plan) -> Unit
) {
    DeleteAlertDialog(
        titleEntity = stringResource(MR.strings.deleting_plan),
        onCancelClick = {
            modalNavController.popBackStack(key, animate = false)
        },
        onDeleteClick = {
            plan?.let {
                onDeleteTransactionClick(it)
            }
        }
    )
}