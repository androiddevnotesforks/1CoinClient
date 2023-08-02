package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.button.SecondaryButton
import com.finance_tracker.finance_tracker.core.ui.dialogs.BottomSheetDialogSurface
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
@Suppress("ReusedModifierInstance")
fun LoadingDialog(
    title: StringResource,
    description: StringResource,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit = {}
) {
    BottomSheetDialogSurface(withDragBar = false) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(title),
                style = CoinTheme.typography.h5,
                color = CoinTheme.color.content
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(description),
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.secondary,
                textAlign = TextAlign.Center
            )
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(vertical = 36.dp)
                    .size(44.dp)
            )
            SecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(MR.strings.btn_cancel),
                onClick = onCancel
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}