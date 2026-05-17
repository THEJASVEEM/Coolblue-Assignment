package com.thejasvee.coolblue.ui.search

import com.thejasvee.coolblue.domain.model.Product

data class SearchUiState(
    val query: String = "",
    val products: List<Product> = emptyList(),
    val totalResults: Int = 0,
    val currentPage: Int = 0,
    val pageCount: Int = 0,
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: String? = null,
    val paginationErrorMessage: String? = null,
    val hasSearched: Boolean = false
) {
    val canLoadMore: Boolean
        get() = hasSearched &&
                !isInitialLoading &&
                !isLoadingMore &&
                errorMessage == null &&
                currentPage < pageCount
}