package com.finance_tracker.finance_tracker.features.settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.button.ActionButton
import dev.icerock.moko.resources.compose.stringResource

@Suppress("ReusedModifierInstance")
@Composable
internal fun SendingUsageDataDialog(
    onOkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CoinTheme {
        Column(
            modifier = modifier
                .background(CoinTheme.color.background)
                .padding(8.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    ),
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                        append(stringResource(MR.strings.sending_usage_data_description_1))
                    }
                    append("\n\n")
                    append(stringResource(MR.strings.sending_usage_data_description_2))
                },
                style = CoinTheme.typography.body1,
                color = CoinTheme.color.content
            )

            Row(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        bottom = 8.dp,
                        end = 16.dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Spacer(modifier = Modifier.weight(1f))

                ActionButton(
                    text = stringResource(MR.strings.okey),
                    onClick = { onOkClick() }
                )
            }
        }
    }
}