import androidx.compose.material.Text
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedSelectTextField

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        CoinOutlinedSelectTextField(
            value = "Type",
            label = {
                Text(
                    text = "Label",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            placeholder = {
                Text(
                    text = "Placeholder",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = CoinTheme.typography.body1.staticTextSize()
                )
            },
            selected = true
        )
    }
}