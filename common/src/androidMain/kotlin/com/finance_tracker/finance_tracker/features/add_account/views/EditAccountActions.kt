package com.finance_tracker.finance_tracker.features.add_account.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.IconActionButton
import com.finance_tracker.finance_tracker.core.ui.button.PrimaryButton
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun EditAccountActions(
    deleteEnabled: Boolean,
    addEnabled: Boolean,
    onDeleteClick: () -> Unit,
    onAddAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.padding(top = 24.dp)
    ) {
        if (deleteEnabled) {
            IconActionButton(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 8.dp
                    ),
                painter = painterResource(MR.images.ic_recycle_bin),
                tint = CoinTheme.color.accentRed,
                onClick = onDeleteClick
            )
        }
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .`if`(!deleteEnabled) { padding(start = 16.dp) }
                .padding(end = 16.dp),
            text = stringResource(MR.strings.edit_account_btn_save),
            onClick = {
                focusManager.clearFocus()
                onAddAccountClick()
            },
            enabled = addEnabled
        )
    }
}