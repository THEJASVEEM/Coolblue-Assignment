package com.thejasvee.coolblue.domain.usecase

import com.thejasvee.coolblue.core.result.AppResult
import com.thejasvee.coolblue.core.result.ErrorMessages
import com.thejasvee.coolblue.core.result.ErrorType
import com.thejasvee.coolblue.domain.model.Product
import com.thejasvee.coolblue.domain.model.ProductSearchResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

private fun productSearchResult(
    products: List<Product> = emptyList(),
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


class SearchProductsUseCaseTest {

    lateinit var fakeProductRepository: FakeProductRepository
    lateinit var useCase: SearchProductsUseCase

    @Before
    fun setup() {
        fakeProductRepository = FakeProductRepository(
            result = AppResult.Success(
                productSearchResult()
            )
        )

        useCase = SearchProductsUseCase(fakeProductRepository)
    }

    @Test
    fun `search valid query return success with data`() = runTest {

        val result = useCase(
            query = "apple",
            page = 1
        )

        assertTrue(result is AppResult.Success)

        assertEquals("apple", fakeProductRepository.receivedQuery)
        assertEquals(1, fakeProductRepository.receivedPage)
        assertEquals(1, fakeProductRepository.callCount)
    }

    @Test
    fun `search blank query return empty success`() = runTest {
        val result = useCase(
            query = "   ",
            page = 1
        )

        assertTrue(result is AppResult.Success)

        val data = (result as AppResult.Success).data

        assertEquals(0, data.products.size)
        assertEquals(0, fakeProductRepository.callCount)
    }

    @Test
    fun `search query is trimmed before calling repository`() = runTest {
        val result = useCase(
            query = " apple  ",
            page = 1
        )

        assertTrue(result is AppResult.Success)
        assertEquals("apple", fakeProductRepository.receivedQuery)
        assertEquals(1, fakeProductRepository.callCount)
    }

    @Test
    fun `search one character return empty success`() = runTest {
        val result = useCase(
            query = "a",
            page = 1,
        )

        assertTrue(result is AppResult.Success)

        val data = (result as AppResult.Success).data

        assertEquals(0, data.products.size)
        assertEquals(0, fakeProductRepository.callCount)
    }

    @Test
    fun `repository error is returned as is`() = runTest {
        val expectedError = AppResult.Error(
            type = ErrorType.NETWORK,
            message = ErrorMessages.NETWORK
        )

        val fakeRepository = FakeProductRepository(
            result = expectedError
        )

        val useCase = SearchProductsUseCase(fakeRepository)

        val result = useCase(query = "apple", page = 1)

        assertTrue(result is AppResult.Error)

        val error = result as AppResult.Error
        assertEquals(1, fakeRepository.callCount)
        assertEquals(ErrorType.NETWORK, error.type)
        assertEquals(ErrorMessages.NETWORK, error.message)
    }
}