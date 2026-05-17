package com.thejasvee.coolblue.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejasvee.coolblue.core.result.AppResult
import com.thejasvee.coolblue.domain.usecase.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())

    val uiState: StateFlow<SearchUiState> = _uiState

    private var searchJob: Job? = null

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.QueryChanged -> onQueryChanged(event.query)
            is SearchUiEvent.SearchSubmitted -> onSearchSubmitted()
            is SearchUiEvent.RetryClicked -> onSearchSubmitted()
            is SearchUiEvent.LoadNextPage -> onLoadNextPage()
        }
    }


    private fun onQueryChanged(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                errorMessage = null,
                paginationErrorMessage = null
            )
        }
    }

    private fun onSearchSubmitted() {
        val query = _uiState.value.query

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isInitialLoading = true,
                    isLoadingMore = false,
                    errorMessage = null,
                    paginationErrorMessage = null,
                    products = emptyList(),
                    totalResults = 0,
                    pageCount = 0,
                    currentPage = 0,
                    hasSearched = true,
                )
            }

            when (val result = searchProductsUseCase(query = query, page = FIRST_PAGE)) {
                is AppResult.Success -> {
                    val data = result.data
                    _uiState.update {
                        it.copy(
                            products = data.products,
                            isInitialLoading = false,
                            totalResults = data.totalResults,
                            pageCount = data.pageCount,
                            currentPage = data.currentPage,
                            errorMessage = null,
                        )
                    }
                }

                is AppResult.Error -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.message,
                            isInitialLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun onLoadNextPage() {
        val state = _uiState.value

        if (!state.canLoadMore) return

        val nextPage = state.currentPage + 1

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingMore = true,
                    paginationErrorMessage = null
                )
            }

            when (val result = searchProductsUseCase(query = state.query, page = nextPage)) {
                is AppResult.Success -> {
                    val data = result.data

                    _uiState.update {
                        it.copy(
                            isLoadingMore = false,
                            paginationErrorMessage = null,
                            products = it.products + data.products,
                            currentPage = data.currentPage,
                            totalResults = data.totalResults,
                            pageCount = data.pageCount,
                        )
                    }
                }

                is AppResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoadingMore = false,
                            paginationErrorMessage = result.message,
                        )
                    }
                }
            }
        }
    }

    private companion object {
        const val FIRST_PAGE = 1
    }
}