package com.thejasvee.coolblue.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thejasvee.coolblue.R
import com.thejasvee.coolblue.ui.search.components.SearchEmptyState
import com.thejasvee.coolblue.ui.search.components.SearchErrorState
import com.thejasvee.coolblue.ui.search.components.SearchHeader
import com.thejasvee.coolblue.ui.search.components.SearchInput
import com.thejasvee.coolblue.ui.search.components.SearchLoadingState
import com.thejasvee.coolblue.ui.search.components.SearchResults
import com.thejasvee.coolblue.ui.theme.CoolblueSpacing
import com.thejasvee.coolblue.ui.theme.initialSearchIconBackgroundColor
import com.thejasvee.coolblue.ui.theme.noResultsIconBackgroundColor

@Composable
fun SearchScreen(
    state: SearchUiState,
    onEvent: (SearchUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = CoolblueSpacing.MobilePadding,
                    end = CoolblueSpacing.MobilePadding,
                    top = 80.dp
                )
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                SearchHeader()

                SearchInput(
                    query = state.query,
                    onQueryChange = { query ->
                        onEvent(SearchUiEvent.QueryChanged(query))
                    },
                    onSearch = {
                        onEvent(SearchUiEvent.SearchSubmitted)
                    },
                    onClearClick = {
                        onEvent(SearchUiEvent.ClearSearchClicked)
                    },
                    modifier = Modifier.padding(
                        top = CoolblueSpacing.Xl,
                        bottom = CoolblueSpacing.Xl
                    ),
                )
                when {
                    state.products.isNotEmpty() -> {
                        SearchResults(
                            products = state.products,
                            isLoadingMore = state.isLoadingMore,
                            paginationErrorMessage = state.paginationErrorMessage,
                            onLoadNextPage = {
                                onEvent(SearchUiEvent.LoadNextPage)
                            },
                            onRetryPagination = {
                                onEvent(SearchUiEvent.LoadNextPage)
                            },
                        )
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(bottom = 96.dp),
                            contentAlignment = Alignment.Center
                        ) {
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
                                    SearchEmptyState(
                                        imageRes = R.drawable.ic_empty_box,
                                        title = "Start searching",
                                        description = "Search for phones to see results",
                                        imageBackgroundColor = initialSearchIconBackgroundColor()
                                    )
                                }

                                state.products.isEmpty() -> {
                                    SearchEmptyState(
                                        imageRes = R.drawable.ic_no_results,
                                        title = "No results found",
                                        description = "We couldn't find any products matching \"${state.query}\"",
                                        imageBackgroundColor = noResultsIconBackgroundColor(),
                                        suggestions = listOf("iPhone", "MacBook", "Apple"),
                                        onSuggestionClick = { suggestion ->
                                            onEvent(SearchUiEvent.QueryChanged(suggestion))
                                            onEvent(SearchUiEvent.SearchSubmitted)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}