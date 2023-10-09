package com.finance_tracker.finance_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.finance_tracker.finance_tracker.core.common.ActivityContextStorage
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.features.root.RootComponent
import com.finance_tracker.finance_tracker.features.root.RootScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityContextStorage.setContext(this)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        val rootComponent = RootComponent(componentContext = defaultComponentContext())

        setContent {
            CoinTheme {
                RootScreen(component = rootComponent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityContextStorage.removeContext()
    }
}