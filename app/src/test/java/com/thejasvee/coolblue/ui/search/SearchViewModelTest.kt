package com.thejasvee.coolblue.ui.search

import com.thejasvee.coolblue.core.result.AppResult
import com.thejasvee.coolblue.core.result.ErrorMessages
import com.thejasvee.coolblue.core.result.ErrorType
import com.thejasvee.coolblue.domain.model.Product
import com.thejasvee.coolblue.domain.model.ProductSearchResult
import com.thejasvee.coolblue.domain.repository.ProductRepository
import com.thejasvee.coolblue.domain.usecase.SearchProductsUseCase
import com.thejasvee.coolblue.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `query changed updates query in state`() {
        val (viewModel, _) = createViewModel()

        viewModel.onEvent(SearchUiEvent.QueryChanged("apple"))

        assertEquals("apple", viewModel.uiState.value.query)
    }

    @Test
    fun `search state updates with products when use case succeeds`() = runTest {
        val products = listOf(
            product(id = 1, name = "Apple 26"),
            product(id = 2, name = "Apple 27")
        )

        val (viewModel, fakeRepository) = createViewModel(
            result = AppResult.Success(
                productSearchResult(
                    products = products,
                    currentPage = 1,
                    pageCount = 1,
                    totalResults = 2,
                )
            )
        )

        viewModel.onEvent(SearchUiEvent.QueryChanged("apple"))
        viewModel.onEvent(SearchUiEvent.SearchSubmitted)

        val state = viewModel.uiState.value

        assertFalse(state.isInitialLoading)
        assertTrue(state.hasSearched)
        assertEquals(2, state.products.size)
        assertEquals(2, state.totalResults)
        assertEquals(1, state.currentPage)
        assertEquals(1, state.pageCount)
        assertEquals(null, state.errorMessage)

        assertEquals(1, fakeRepository.callCount)
        assertEquals("apple", fakeRepository.receivedQuery)
        assertEquals(1, fakeRepository.receivedPage)
    }

    @Test
    fun `search state updates with error when use case fails`() = runTest {
        val (viewModel, _) = createViewModel(
            result = AppResult.Error(
                type = ErrorType.NETWORK,
                message = ErrorMessages.NETWORK
            )
        )

        viewModel.onEvent(SearchUiEvent.QueryChanged("apple"))
        viewModel.onEvent(SearchUiEvent.SearchSubmitted)

        val state = viewModel.uiState.value

        assertFalse(state.isInitialLoading)
        assertTrue(state.hasSearched)
        assertTrue(state.products.isEmpty())
        assertEquals(
            ErrorMessages.NETWORK,
            state.errorMessage
        )
    }

}

private class FakeProductRepository(
    var result: AppResult<ProductSearchResult>
) : ProductRepository {

    var callCount: Int = 0
        private set

    var receivedQuery: String? = null
        private set

    var receivedPage: Int? = null
        private set

    override suspend fun searchProducts(
        query: String,
        page: Int
    ): AppResult<ProductSearchResult> {
        callCount++
        receivedQuery = query
        receivedPage = page
        return result
    }
}

private fun product(
    id: Int = 1,
    name: String = "Apple iPhone"
): Product {
    return Product(
        id = id,
        name = name,
        imageUrl = "https://image.coolblue.nl/product.png",
        price = 999.0,
        listPrice = null,
        productReviewSummary = null,
        usps = emptyList(),
        promo = null,
        nextDayDelivery = true
    )
}

private fun productSearchResult(
    products: List<Product> = listOf(product()),
    currentPage: Int = 1,
    pageSize: Int = 24,
    totalResults: Int = products.size,
    pageCount: Int = 1
): ProductSearchResult {
    return ProductSearchResult(
        products = products,
        currentPage = currentPage,
        pageSize = pageSize,
        totalResults = totalResults,
        pageCount = pageCount
    )
}

private fun createViewModel(
    result: AppResult<ProductSearchResult> = AppResult.Success(productSearchResult())
): Pair<SearchViewModel, FakeProductRepository> {
    val fakeRepository = FakeProductRepository(result)
    val useCase = SearchProductsUseCase(fakeRepository)
    val viewModel = SearchViewModel(useCase)

    return viewModel to fakeRepository
}