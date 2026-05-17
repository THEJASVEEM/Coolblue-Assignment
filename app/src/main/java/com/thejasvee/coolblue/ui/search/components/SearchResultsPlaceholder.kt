package com.thejasvee.coolblue.ui.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchResultsPlaceholder(
    modifier: Modifier = Modifier,
    productCount: Int,
    totalResults: Int,
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Found $totalResults products")
        Text(text = "Showing $productCount products")
    }
}