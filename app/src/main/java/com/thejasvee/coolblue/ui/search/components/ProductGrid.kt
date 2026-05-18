package com.thejasvee.coolblue.ui.search.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thejasvee.coolblue.domain.model.Product
import com.thejasvee.coolblue.ui.theme.CoolblueRadius
import com.thejasvee.coolblue.ui.theme.CoolblueSpacing

@Composable
fun ProductGrid(
    products: List<Product>,
    isLoadingMore: Boolean,
    paginationErrorMessage: String?,
    onLoadNextPage: () -> Unit,
    onRetryPagination: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItemIndex =
                gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            val totalItemsCount = gridState.layoutInfo.totalItemsCount

            !isLoadingMore &&
                    paginationErrorMessage == null &&
                    totalItemsCount > 0 &&
                    lastVisibleItemIndex >= totalItemsCount - 4
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadNextPage()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = modifier.testTag("ProductGrid"),
        contentPadding = PaddingValues(
            bottom = CoolblueSpacing.Xl
        ),
        horizontalArrangement = Arrangement.spacedBy(CoolblueSpacing.Md),
        verticalArrangement = Arrangement.spacedBy(CoolblueSpacing.Lg)
    ) {
        items(
            items = products,
            key = { product -> product.id },
            contentType = { "product" }
        ) { product ->
            ProductCard(product = product)
        }

        if (isLoadingMore) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                PaginationLoadingFooter()
            }
        }

        if (paginationErrorMessage != null) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                PaginationErrorFooter(
                    message = paginationErrorMessage,
                    onRetryClick = onRetryPagination,
                    modifier = Modifier.testTag("PaginationErrorFooter")

                )
            }
        }
    }
}

@Composable
private fun PaginationLoadingFooter(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = CoolblueSpacing.Lg),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun PaginationErrorFooter(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = CoolblueSpacing.Xl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        LaunchedEffect(message) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

        AssistChip(
            onClick = onRetryClick,
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
            ),
            modifier = Modifier.testTag("PaginationRetryButton")
        )
    }
}