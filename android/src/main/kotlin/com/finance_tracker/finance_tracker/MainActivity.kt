package com.finance_tracker.finance_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.navigation.main.navigationGraph
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.odyssey.core.configuration.DisplayType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        setContent {
            CoinTheme {
                setNavigationContent(
                    configuration = OdysseyConfiguration(
                        canvas = this,
                        startScreen = StartScreen.Custom(MainNavigationTree.Welcome.name),
                        backgroundColor = CoinTheme.color.background,
                        displayType = DisplayType.EdgeToEdge
                    ),
                    onApplicationFinish = ::finish
                ) {
                    navigationGraph()
                }
            }
        }
    }
}