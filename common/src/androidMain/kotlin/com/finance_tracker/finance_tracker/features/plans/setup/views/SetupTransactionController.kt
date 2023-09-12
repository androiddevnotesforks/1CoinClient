package com.finance_tracker.finance_tracker.features.plans.setup.views

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
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.features.add_transaction.KeyboardCommand
import com.finance_tracker.finance_tracker.features.add_transaction.views.enter_transaction_controller.AmountKeyboard
import com.finance_tracker.finance_tracker.features.add_transaction.views.enter_transaction_controller.CategorySelector
import com.finance_tracker.finance_tracker.features.plans.setup.SetupPlanStep

private const val ContentAnimationDuration = 200

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun SetupPlanController(
    categories: List<Category>,
    currentStep: SetupPlanStep?,
    animationDirection: Int,
    modifier: Modifier = Modifier,
    onCategorySelect: (Category) -> Unit = {},
    onKeyboardButtonClick: (KeyboardCommand) -> Unit = {}
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

                SetupPlanStep.Category -> {
                    CategorySelector(
                        withAddButton = false,
                        categories = categories,
                        onCategorySelect = onCategorySelect,
                        onCategoryAdd = { /*onCategoryAdd*/ }
                    )
                }

                SetupPlanStep.PrimaryAmount -> {
                    AmountKeyboard(
                        onButtonClick = onKeyboardButtonClick
                    )
                }

                else -> {
                    /* no keyboard */
                }
            }
        }
    }
}