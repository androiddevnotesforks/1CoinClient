package com.finance_tracker.finance_tracker.core.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.imePadding
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.button.ActionButton
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun DeleteAlertDialog(
    titleEntity: String,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    AlertDialogSurface {
        DeleteDialog(
            titleEntity = titleEntity,
            onCancelClick = onCancelClick,
            onDeleteClick = onDeleteClick
        )
    }
}

@Composable
internal fun DeleteBottomDialog(
    titleEntity: String,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    BottomSheetDialogSurface {
        DeleteDialog(
            titleEntity = titleEntity,
            onCancelClick = onCancelClick,
            onDeleteClick = onDeleteClick,
            modifier = Modifier
                .navigationBarsPadding()
                .imePadding()
        )
    }
}

@Composable
private fun DeleteDialog(
    titleEntity: String,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 16.dp),
            text = titleEntity,
            style = CoinTheme.typography.h4,
            color = CoinTheme.color.content
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
            text = stringResource(MR.strings.dialog_body_delete),
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
                text = stringResource(MR.strings.btn_cancel),
                onClick = { onCancelClick() }
            )

            Spacer(modifier = Modifier.width(24.dp))

            ActionButton(
                text = stringResource(MR.strings.btn_delete),
                textColor = CoinTheme.color.accentRed,
                onClick = { onDeleteClick() }
            )
        }
    }
}