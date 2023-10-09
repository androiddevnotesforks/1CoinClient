package com.finance_tracker.finance_tracker.features.plans.overview.views.set_limit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.common.R
import com.finance_tracker.finance_tracker.core.common.toTextFieldValue
import com.finance_tracker.finance_tracker.core.common.toUiTextFieldValue
import com.finance_tracker.finance_tracker.core.ui.button.PrimaryButton
import com.finance_tracker.finance_tracker.core.ui.dialogs.BottomSheetDialogSurface
import com.finance_tracker.finance_tracker.core.ui.keyboard.ArithmeticKeyboard
import com.finance_tracker.finance_tracker.features.plans.overview.views.set_limit.views.AmountTextField
import com.finance_tracker.finance_tracker.features.plans.set_limit.SetLimitComponent

@Composable
fun SetLimitDialog(component: SetLimitComponent) {
    BottomSheetDialogSurface(
        onDismissRequest = { component.viewModel.onDismissRequest() }
    ) {
        val viewModel = component.viewModel
        val state by viewModel.state.collectAsState()
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            viewModel.setYearMonth(component.yearMonth)

            focusRequester.requestFocus()
        }

        Column {

            AmountTextField(
                modifier = Modifier.focusRequester(focusRequester),
                enteredBalance = state.balance.toUiTextFieldValue(),
                isError = false,
                currency = state.primaryCurrency,
                onAmountChange = { uiTextFieldValue ->
                    viewModel.onAmountChange(uiTextFieldValue.toTextFieldValue())
                }
            )

            ArithmeticKeyboard(
                shouldShowAmountKeyboard = true,
                onKeyboardClick = viewModel::onKeyboardButtonClick,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .heightIn(max = 290.dp),
                isBottomExpandable = false,
                hasNavBarInsets = false,
                hasElevation = false,
                isArithmeticOperationsVisible = false,
                hasBackground = false
            )

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.plans_amount_btn_set_limit),
                onClick = viewModel::onSetLimitClick
            )
        }
    }
}