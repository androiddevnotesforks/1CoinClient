package com.finance_tracker.finance_tracker.features.preset_currency.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.domain.models.getDisplayName
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Currency
import dev.icerock.moko.resources.compose.painterResource

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun SelectedCurrency(
    currency: Currency,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = CoinTheme.color.primary,
                shape = RoundedCornerShape(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val text = "${currency.code} - ${currency.getDisplayName()}"
        AnimatedContent(
            targetState = text,
            transitionSpec = {
                @Suppress("MagicNumber")
                fadeIn(animationSpec = tween(350, delayMillis = 100)) with
                        fadeOut(animationSpec = tween(100))
            }
        ) { targetText ->
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                text = targetText,
                style = CoinTheme.typography.body1,
                maxLines = 1
            )
        }

        Spacer(Modifier.weight(1f))

        Icon(
            modifier = Modifier
                .padding(end = 12.dp)
                .padding(vertical = 10.dp)
                .size(20.dp),
            painter = painterResource(MR.images.ic_done),
            contentDescription = null,
            tint = CoinTheme.color.primary,
        )
    }
}