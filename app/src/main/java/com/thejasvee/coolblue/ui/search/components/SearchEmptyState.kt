package com.thejasvee.coolblue.ui.search.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thejasvee.coolblue.ui.theme.CoolblueSpacing

@Composable
fun SearchEmptyState(
    @DrawableRes imageRes: Int,
    title: String,
    description: String,
    imageBackgroundColor: Color,
    modifier: Modifier = Modifier,
    backgroundSize: Dp = 120.dp,
    imageSize: Dp = 55.dp,
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(backgroundSize)
                .clip(CircleShape)
                .background(imageBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(imageSize)
            )
        }

        Text(
            text = title,
            modifier = Modifier.padding(top = CoolblueSpacing.Xl),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = description,
            modifier = Modifier.padding(top = CoolblueSpacing.Md),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (suggestions.isNotEmpty()) {
            SearchSuggestions(
                suggestions = suggestions,
                onSuggestionClick = onSuggestionClick,
                modifier = Modifier.padding(top = CoolblueSpacing.Xxl)
            )
        }
    }
}