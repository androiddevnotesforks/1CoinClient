package com.finance_tracker.finance_tracker.features.add_category.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinGridDropdownMenu
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
internal fun ChooseIconButton(
    chosenIcon: ImageResource,
    icons: List<ImageResource>,
    onIconChoose: (ImageResource) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(chosenIcon),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .border(
                    width = 1.dp,
                    color = CoinTheme.color.dividers,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    expanded = true
                    onClick()
                }
                .padding(12.dp)
                .align(Alignment.Center),
            tint = CoinTheme.color.content
        )

        Box {
            CoinGridDropdownMenu(
                modifier = Modifier
                    .requiredSizeIn(maxWidth = 300.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                columnSize = 40.dp,
                yOffset = 52.dp
            ) {
                items(icons) { iconId ->
                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(0.5.dp)
                            .clip(CircleShape)
                            .clickable {
                                expanded = false
                                onIconChoose(iconId)
                            }
                            .padding(7.5.dp),
                        painter = painterResource(iconId),
                        contentDescription = null,
                        tint = CoinTheme.color.content
                    )
                }
            }
        }
    }
}
