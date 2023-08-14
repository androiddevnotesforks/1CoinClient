package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.common.toUIColor
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Account
import dev.icerock.moko.resources.compose.painterResource
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable

val AccountCardHeight = 128.dp
val AccountCardWidth = 160.dp
private const val AccountCardAspectRatio = 1.3F

@Composable
internal fun AccountCard(
    data: Account,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    Surface(
        elevation = elevation,
        shape = RoundedCornerShape(12.dp),
        color = data.colorModel.color.toUIColor(),
        modifier = modifier
            .aspectRatio(AccountCardAspectRatio)
            .scaleClickAnimation()
            .noRippleClickable(onClick)
    ) {
        Column {
            Icon(
                painter = painterResource(data.icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
                    .size(29.dp),
                tint = CoinTheme.color.white
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = data.balance.format(mode = AmountFormatMode.NegativeSign),
                style = CoinTheme.typography.h5,
                color = CoinTheme.color.white,
                modifier = Modifier
                    .padding(start = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val accountName = if (data.type == Account.Type.Cash) {
                "${data.name} (${data.balance.currency.symbol})"
            } else {
                data.name
            }
            Text(
                text = accountName,
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.white.copy(alpha = 0.5f),
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        bottom = 16.dp
                    ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}