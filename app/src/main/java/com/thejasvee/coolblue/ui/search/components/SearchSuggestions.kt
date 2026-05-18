package com.thejasvee.coolblue.ui.search.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thejasvee.coolblue.ui.theme.CoolblueRadius
import com.thejasvee.coolblue.ui.theme.CoolblueSpacing

@Composable
fun SearchSuggestions(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(CoolblueRadius.Lg),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = CoolblueSpacing.SectionGap,
                vertical = CoolblueSpacing.Lg
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Try searching for:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            FlowRow(
                modifier = Modifier.padding(top = CoolblueSpacing.Md),
                horizontalArrangement = Arrangement.spacedBy(CoolblueSpacing.Md),
                verticalArrangement = Arrangement.spacedBy(CoolblueSpacing.Sm)
            ) {
                suggestions.forEach { suggestion ->
                    AssistChip(
                        onClick = {
                            onSuggestionClick(suggestion)
                        },
                        label = {
                            Text(text = suggestion)
                        },
                        shape = RoundedCornerShape(50),
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = MaterialTheme.colorScheme.onSurface
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        }
    }
}