package com.finance_tracker.finance_tracker.presentation.settings_sheet.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun SendingUsageDataDialog(
    onOkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp),
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
                    append(stringResource("sending_usage_data_description_1"))
                }
                append("\n\n")
                append(stringResource("sending_usage_data_description_2"))
            },
            style = CoinTheme.typography.body2
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
                text = stringResource("okey"),
                onClick = { onOkClick.invoke() }
            )
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = CoinTheme.color.content,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick.invoke() }
            .padding(8.dp),
        text = text,
        style = CoinTheme.typography.body2_medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = textColor,
        textAlign = TextAlign.Center
    )
}