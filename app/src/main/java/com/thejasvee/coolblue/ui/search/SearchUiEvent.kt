package com.thejasvee.coolblue.ui.search

sealed interface SearchUiEvent {

    data class QueryChanged(
        val query: String
    ) : SearchUiEvent

    data object SearchSubmitted : SearchUiEvent

    data object RetryClicked : SearchUiEvent

    data object LoadNextPage : SearchUiEvent

    data object ClearSearchClicked : SearchUiEvent
}