package com.finance_tracker.finance_tracker.features.add_transaction.views.enter_transaction_controller

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.keyboard.KeyboardAction
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.keyboard.ArithmeticKeyboard
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.features.add_transaction.EnterTransactionStep

private const val ContentAnimationDuration = 200

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun EnterTransactionController(
    categories: List<Category>,
    currentStep: EnterTransactionStep?,
    animationDirection: Int,
    accounts: List<Account>,
    modifier: Modifier = Modifier,
    onAccountSelect: (Account) -> Unit = {},
    onAccountAdd: () -> Unit = {},
    onCategorySelect: (Category) -> Unit = {},
    onCategoryAdd: () -> Unit = {},
    onKeyboardButtonClick: (KeyboardAction) -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(CoinTheme.color.secondaryBackground)
            .fillMaxSize()
    ) {
        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(ContentAnimationDuration),
                    initialOffsetX = { fullWidth -> animationDirection * fullWidth }
                ) with slideOutHorizontally(
                    animationSpec = tween(ContentAnimationDuration),
                    targetOffsetX = { fullWidth -> -animationDirection * fullWidth }
                )
            }
        ) { targetStep ->
            when (targetStep) {
                EnterTransactionStep.PrimaryAccount,
                EnterTransactionStep.SecondaryAccount-> {
                    AccountSelector(
                        accounts = accounts,
                        onAccountSelect = onAccountSelect,
                        onAccountAdd = onAccountAdd
                    )
                }

                EnterTransactionStep.Category -> {
                    CategorySelector(
                        withAddButton = true,
                        categories = categories,
                        onCategorySelect = onCategorySelect,
                        onCategoryAdd = onCategoryAdd
                    )
                }

                EnterTransactionStep.PrimaryAmount,
                EnterTransactionStep.SecondaryAmount -> {
                        ArithmeticKeyboard(
                            shouldShowAmountKeyboard = true,
                            isBottomExpandable = false,
                            onKeyboardClick = onKeyboardButtonClick
                        )
                    }
                else -> {
                    /* no keyboard */
                }
            }
        }
    }
}