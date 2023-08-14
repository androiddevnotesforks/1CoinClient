package com.finance_tracker.finance_tracker.core.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.LocalSystemBarsConfig
import com.finance_tracker.finance_tracker.core.common.asSp
import com.finance_tracker.finance_tracker.core.common.getContext
import com.finance_tracker.finance_tracker.core.common.getFixedInsets
import com.finance_tracker.finance_tracker.core.common.getKoin
import com.finance_tracker.finance_tracker.core.common.updateSystemBarsConfig
import com.finance_tracker.finance_tracker.core.ui.snackbar.CoinSnackbarHostState
import com.finance_tracker.finance_tracker.domain.interactors.ThemeInteractor
import com.finance_tracker.finance_tracker.domain.models.ThemeMode
import dev.icerock.moko.resources.ImageResource
import com.finance_tracker.finance_tracker.core.theme.toJvmColorPalette as toJvmColorPalette1

private val themeInteractor: ThemeInteractor by lazy { getKoin().get() }

val LocalDarkTheme = staticCompositionLocalOf {
    false
}

val LocalCoinColors = staticCompositionLocalOf { val toJvmColorPalette =
    ColorPalette.Undefined.toJvmColorPalette1()
    toJvmColorPalette
}
val LocalCoinTypography = staticCompositionLocalOf {
    CoinTypography()
}
val LocalCoinElevation = staticCompositionLocalOf {
    CoinElevation(
        default = 4.dp,
        pressed = 8.dp
    )
}
val LocalCoinShapes = staticCompositionLocalOf {
    CoinShapes()
}

val LocalCoinSnackbarHostState = staticCompositionLocalOf<CoinSnackbarHostState?> { null }

// Ripple

@Immutable
private object CoinRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = LocalContentColor.current,
        lightTheme = true
    )

    @Composable
    override fun rippleAlpha() = DefaultRippleAlpha
}

private val DefaultRippleAlpha = RippleAlpha(
    pressedAlpha = 0.08f,
    focusedAlpha = 0.08f,
    draggedAlpha = 0.12f,
    hoveredAlpha = 0.06f
)

object CoinAlpha {
    const val Medium = 0.2f
    const val Soft = 0.8f
}

@Composable
internal fun TextStyle.staticTextSize(isStaticContentSize: Boolean = true): TextStyle {
    return if (isStaticContentSize) copy(fontSize = fontSize.value.dp.asSp()) else this
}

@Composable
fun provideThemeImage(
    darkFile: ImageResource,
    lightFile: ImageResource
): ImageResource {
    return if (LocalDarkTheme.current) {
        darkFile
    } else {
        lightFile
    }
}

@Composable
fun CoinTheme(
    content: @Composable () -> Unit
) {
    val themeMode by themeInteractor.getThemeMode().collectAsState()

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val isDarkTheme by remember(themeMode, isSystemInDarkTheme) {
        derivedStateOf {
            when (themeMode) {
                ThemeMode.System -> isSystemInDarkTheme
                ThemeMode.Light -> false
                ThemeMode.Dark -> true
            }
        }
    }

    val coinColors by remember(isDarkTheme) {
        derivedStateOf { ColorPalette.provideColorPalette(isDarkTheme).toJvmColorPalette1() }
    }

    val context = getContext()
    context.updateSystemBarsConfig(
        systemBarsConfig = LocalSystemBarsConfig.current,
        isDarkTheme = isDarkTheme
    )

    MaterialTheme(
        typography = MaterialTheme.typography.copy(
            subtitle1 = CoinTheme.typography.body1,
            caption = CoinTheme.typography.subtitle2
        ),
        colors = MaterialTheme.colors.copy(
            background = coinColors.switch().background,
            onBackground = coinColors.switch().content,
            surface = CoinTheme.color.switch().backgroundSurface,
            primary = coinColors.switch().primary,
            primaryVariant = coinColors.switch().primaryVariant
        ),
        shapes = Shapes(
            medium = CoinTheme.shapes.medium
        )
    ) {
        CompositionLocalProvider(
            LocalContext provides context,
            LocalCoinColors provides coinColors.switch(),
            LocalCoinTypography provides CoinTheme.typography,
            LocalCoinShapes provides CoinTheme.shapes,
            LocalCoinElevation provides CoinTheme.elevation,
            LocalDarkTheme provides isDarkTheme,
            LocalContentColor provides CoinTheme.color.content,
            LocalIndication provides rememberRipple(),
            LocalRippleTheme provides CoinRippleTheme,
            LocalTextStyle provides CoinTheme.typography.body1,
            LocalFixedInsets provides getFixedInsets(),
            content = content
        )
    }
}

object CoinPaddings {
    val bottomNavigationBar = 96.dp
}

object CoinTheme {
    val color: JvmCoinColors
        @Composable
        get() = LocalCoinColors.current
    val typography: CoinTypography
        @Composable
        get() = LocalCoinTypography.current
    val elevation: CoinElevation
        @Composable
        get() = LocalCoinElevation.current
    val shapes: CoinShapes
        @Composable
        get() = LocalCoinShapes.current
}