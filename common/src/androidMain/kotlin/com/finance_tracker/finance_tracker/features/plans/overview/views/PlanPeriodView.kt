package com.finance_tracker.finance_tracker.features.plans.overview.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.date.localizedName
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.features.plans.overview.views.plan_period.PlanPeriodCallback
import com.finance_tracker.finance_tracker.features.plans.overview.views.plan_period.PlanPeriodState
import dev.icerock.moko.resources.compose.painterResource

private const val AnimationDuration = 350
private const val AnimationDelay = 50
private const val AnimationOffsetMultiplyer = 1.5f

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Suppress("ModifierHeightWithText")
internal fun PlanPeriodView(
    state: PlanPeriodState,
    callback: PlanPeriodCallback,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .background(CoinTheme.color.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                painter = painterResource(MR.images.ic_arrow_left_small),
                enabled = state.isPreviousPeriodEnabled,
                onClick = callback::onPreviousPeriodClick
            )

            val context = LocalContext.current
            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = state.selectedPeriod,
                transitionSpec = {
                    val animationCoefficient = if (initialState > targetState) 1 else -1
                    slideInHorizontally(
                        animationSpec = tween(AnimationDuration, delayMillis = AnimationDelay),
                        initialOffsetX = { (-it * AnimationOffsetMultiplyer * animationCoefficient).toInt() }
                    ) with slideOutHorizontally(
                        animationSpec = tween(AnimationDuration),
                        targetOffsetX = { (it * AnimationOffsetMultiplyer * animationCoefficient).toInt() }
                    )
                },
                label = "AnimatedContent"
            ) { targetState ->
                Text(
                    text = targetState.localizedName(context),
                    style = CoinTheme.typography.body1_medium,
                    textAlign = TextAlign.Center
                )
            }

            IconButton(
                painter = painterResource(MR.images.ic_arrow_right_small),
                enabled = state.isNextPeriodEnabled,
                onClick = callback::onNextPeriodClick
            )
        }
        TabRowDefaults.Divider(
            color = CoinTheme.color.dividers
        )
    }
}

@Composable
private fun IconButton(
    painter: Painter,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier
            .padding(4.dp)
            .size(24.dp)
            .clip(CircleShape)
            .clickable(enabled) { onClick() },
        painter = painter,
        contentDescription = null,
        tint = if (enabled) {
            CoinTheme.color.secondary
        } else {
            CoinTheme.color.dividers
        }
    )
}