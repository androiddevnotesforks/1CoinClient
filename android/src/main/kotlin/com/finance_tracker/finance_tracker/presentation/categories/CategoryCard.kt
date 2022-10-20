package com.finance_tracker.finance_tracker.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Category

@Composable
fun CategoryCard(
    data: Category,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .size(
                width = 296.dp,
                height = 44.dp
            ),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_more_vert),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(18.dp)
                .align(Alignment.CenterVertically)
        )
        Icon(
            painter = painterResource(data.icon),
            contentDescription = null,
            Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(
                    color = CoinTheme.color.secondaryBackground,
                    shape = CircleShape
                )
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            tint = CoinTheme.color.content
        )
        Text(
            text = data.name,
            style = CoinTheme.typography.body2,
            modifier = Modifier
                .padding(start = 12.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.ic_plus),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 6.dp)
                .size(9.dp)
                .align(Alignment.CenterVertically),
            tint = Color.Red
        )
    }
}

@Preview
@Composable
fun CategoryCardPreview() {
    CategoryCard(
        Category(
            id = 0,
            name = "Shopping",
            icon = R.drawable.ic_category_9,
        )
    )
}