package com.thejasvee.coolblue.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thejasvee.coolblue.R
import com.thejasvee.coolblue.ui.search.components.SearchEmptyState
import com.thejasvee.coolblue.ui.search.components.SearchErrorState
import com.thejasvee.coolblue.ui.search.components.SearchHeader
import com.thejasvee.coolblue.ui.search.components.SearchInput
import com.thejasvee.coolblue.ui.search.components.SearchLoadingState
import com.thejasvee.coolblue.ui.search.components.SearchResultsPlaceholder

@Composable
fun SearchScreen(
    state: SearchUiState,
    onEvent: (SearchUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 100.dp,
                bottom = 0.dp
            )
    ) {
        SearchHeader()

        SearchInput(
            query = state.query,
            onQueryChange = { query ->
                onEvent(SearchUiEvent.QueryChanged(query))
            },
            onSearch = {
                onEvent(SearchUiEvent.SearchSubmitted)
            }
        )

        when {
            state.isInitialLoading -> {
                SearchLoadingState()
            }

            state.errorMessage != null -> {
                SearchErrorState(
                    message = state.errorMessage,
                    onRetryClick = {
                        onEvent(SearchUiEvent.RetryClicked)
                    }
                )
            }

            !state.hasSearched -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 96.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SearchEmptyState(
                        imageRes = R.drawable.ic_empty_box,
                        title = "Start searching",
                        description = "Search for phones to see results",
                        imageBackgroundColor = Color(0xFFFFE4BD)
                    )
                }

            }

            state.products.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 72.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SearchEmptyState(
                        imageRes = R.drawable.ic_no_results,
                        title = "No results found",
                        description = "We couldn't find any products matching \"${state.query}\"",
                        imageBackgroundColor = Color(0xFFEDEFF3)
                    )
                }
            }

            else -> {
                SearchResultsPlaceholder(
                    productCount = state.products.size,
                    totalResults = state.totalResults
                )
            }
        }
    }

}