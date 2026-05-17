package com.thejasvee.coolblue.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.thejasvee.coolblue.ui.search.components.InitialSearchEmptyState
import com.thejasvee.coolblue.ui.search.components.NoProductsFoundState
import com.thejasvee.coolblue.ui.search.components.SearchErrorState
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
        Text(text = "Hello, what phones are you looking for today?")

        OutlinedTextField(
            value = state.query,
            onValueChange = { query ->
                onEvent(SearchUiEvent.QueryChanged(query))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            placeholder = {
                Text(text = "Search for phones")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onEvent(SearchUiEvent.SearchSubmitted)
                }
            )
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
                InitialSearchEmptyState()
            }

            state.products.isEmpty() -> {
                NoProductsFoundState()
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