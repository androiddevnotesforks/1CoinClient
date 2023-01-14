package com.finance_tracker.finance_tracker.presentation.add_account.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.PrimaryButton
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountViewModel

@Composable
internal fun EditAccountActions(
    viewModel: AddAccountViewModel,
    addEnable: Boolean,
    account: Account,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 8.dp
                )
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color = CoinTheme.color.secondaryBackground,
                    shape = RoundedCornerShape(12.dp),
                )
                .clickable { viewModel.onDeleteClick(account) },
        ) {
            Icon(
                painter = rememberVectorPainter("ic_recycle_bin"),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 8.dp
                    )
                    .size(24.dp),
                tint = CoinTheme.color.accentRed
            )
        }
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            text = stringResource("edit_account_btn_save"),
            onClick = viewModel::onAddAccountClick,
            enable = addEnable
        )
    }
}