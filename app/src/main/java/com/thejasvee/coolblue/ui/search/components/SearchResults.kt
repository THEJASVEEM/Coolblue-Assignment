package com.thejasvee.coolblue.ui.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thejasvee.coolblue.domain.model.Product

@Composable
fun SearchResults(
    products: List<Product>,
    isLoadingMore: Boolean,
    paginationErrorMessage: String?,
    onLoadNextPage: () -> Unit,
    onRetryPagination: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Found ${products.size} products",
            modifier = Modifier.padding(top = 20.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        ProductGrid(
            products = products,
            isLoadingMore = isLoadingMore,
            paginationErrorMessage = paginationErrorMessage,
            onLoadNextPage = onLoadNextPage,
            onRetryPagination = onRetryPagination,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 16.dp)
        )
    }
}