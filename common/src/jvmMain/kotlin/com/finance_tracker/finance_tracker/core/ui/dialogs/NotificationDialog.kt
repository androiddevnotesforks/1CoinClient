package com.finance_tracker.finance_tracker.core.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.button.ActionButton
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun NotificationDialog(
    text: StringResource,
    onOkClick: () -> Unit
) {
    AlertDialogSurface {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                text = stringResource(text),
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
                ActionButton(
                    text = stringResource(MR.strings.okey),
                    onClick = onOkClick
                )
            }
        }
    }
}