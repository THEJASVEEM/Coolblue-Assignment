package com.thejasvee.coolblue.ui.search.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thejasvee.coolblue.R
import com.thejasvee.coolblue.ui.theme.CoolblueRadius
import com.thejasvee.coolblue.ui.theme.CoolblueSpacing

@Composable
fun SearchErrorState(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(message) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchEmptyState(
            imageRes = R.drawable.ic_no_results,
            title = "Error searching",
            description = "Please try again after sometime",
            imageBackgroundColor = MaterialTheme.colorScheme.error.copy(alpha = 0.35f),
        )

        AssistChip(
            onClick = onRetryClick,
            modifier = Modifier.padding(top = CoolblueSpacing.Md),
            label = {
                Text(
                    text = "Retry",
                    fontWeight = FontWeight.Medium
                )
            },
            shape = RoundedCornerShape(CoolblueRadius.Pill),
            colors = AssistChipDefaults.assistChipColors(
                containerColor = MaterialTheme.colorScheme.surface,
                labelColor = MaterialTheme.colorScheme.error
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.error.copy(alpha = 0.35f)
            )
        )
    }
}