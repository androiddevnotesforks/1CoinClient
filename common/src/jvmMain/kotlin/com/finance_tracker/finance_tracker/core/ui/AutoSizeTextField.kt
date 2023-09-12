package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.createFontFamilyResolver

private const val TextScaleReductionInterval = 0.9f

@ExperimentalMaterialApi
@Composable
internal fun AutoSizeTextField(
    value: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit = {},
    onSizeChange: (TextUnit) -> Unit = {},
    onClick: () -> Unit = {}
) {
    val source = remember { MutableInteractionSource() }
    BoxWithConstraints(
        modifier = modifier
    ) {
        var shrunkFontSize = style.fontSize
        val calculateIntrinsics = @Composable {
            ParagraphIntrinsics(
                text = value,
                style = style.copy(
                    fontSize = shrunkFontSize
                ),
                density = LocalDensity.current,
                fontFamilyResolver = createFontFamilyResolver(LocalContext.current)
            )
        }

        var intrinsics = calculateIntrinsics()
        with(LocalDensity.current) {
            // TextField and OutlinedText field have default horizontal padding of 16.dp
            val textFieldDefaultHorizontalPadding = 16.dp.toPx()
            val maxInputWidth = maxWidth.toPx() - 2 * textFieldDefaultHorizontalPadding
            while (intrinsics.maxIntrinsicWidth > maxInputWidth) {
                shrunkFontSize *= TextScaleReductionInterval
                intrinsics = calculateIntrinsics()
            }
            onSizeChange(shrunkFontSize)
        }

        if (source.collectIsPressedAsState().value) {
            onClick()
        }

        val scroll = rememberScrollState()
        BasicTextField(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .horizontalScroll(scroll, false), // Remove horizontal scroll for TextField
            value = value,
            interactionSource = source,
            onValueChange = onValueChange,
            textStyle = style.copy(
                fontSize = shrunkFontSize
            ),
            readOnly = readOnly,
            singleLine = true
        )
    }
}