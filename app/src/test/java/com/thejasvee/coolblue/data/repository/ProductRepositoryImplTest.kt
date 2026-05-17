package com.thejasvee.coolblue.data.repository

import com.thejasvee.coolblue.core.result.AppResult
import com.thejasvee.coolblue.core.result.ErrorMessages
import com.thejasvee.coolblue.core.result.ErrorType
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class ProductRepositoryImplTest {

    @Test
    fun `search products returns success when remote data source succeeds`() = runTest {

        val dataSource = FakeProductRemoteDataSource(
            response = searchProductResponseDto()
        )

        val repository = ProductRepositoryImpl(
            productRemoteDatasource = dataSource
        )

        val result = repository.searchProducts(
            query = "Apple",
            page = 1,
        )

        assertTrue(result is AppResult.Success)
        val data = (result as AppResult.Success).data
        assertEquals(1, data.products.size)
        assertEquals(785359, data.products.first().id)
    }

    @Test
    fun `search products returns error when remote data source failure`() = runTest {
        val dataSource = FakeProductRemoteDataSource(
            exception = Exception()
        )

        val repository = ProductRepositoryImpl(
            productRemoteDatasource = dataSource
        )

        val result = repository.searchProducts(
            query = "Apple",
            page = 1,
        )

        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        val errorMessage = error.message
        val type = error.type
        assertEquals(ErrorMessages.UNKNOWN, errorMessage)
        assertEquals(ErrorType.UNKNOWN, type)
    }

    @Test
    fun `search products returns network error when remote data throws IOException`() = runTest {
        val dataSource = FakeProductRemoteDataSource(
            exception = IOException()
        )

        val repository = ProductRepositoryImpl(
            productRemoteDatasource = dataSource
        )

        val result = repository.searchProducts(
            query = "Apple",
            page = 1,
        )

        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        val errorMessage = error.message
        val type = error.type
        assertEquals(ErrorMessages.NETWORK, errorMessage)
        assertEquals(ErrorType.NETWORK, type)
    }


    @Test
    fun `search products returns serialization error when remote data throws serialization exception`() =
        runTest {
            val dataSource = FakeProductRemoteDataSource(
                exception = SerializationException()
            )

            val repository = ProductRepositoryImpl(
                productRemoteDatasource = dataSource
            )

            val result = repository.searchProducts(
                query = "Apple",
                page = 1,
            )

            assertTrue(result is AppResult.Error)
            val error = result as AppResult.Error
            val errorMessage = error.message
            val type = error.type
            assertEquals(ErrorMessages.SERIALIZATION, errorMessage)
            assertEquals(ErrorType.SERIALIZATION, type)
        }

    @Test
    fun `search products returns server error when remote data throws HttpException`() = runTest {
        val dataSource = FakeProductRemoteDataSource(
            exception = httpException()
        )

        val repository = ProductRepositoryImpl(
            productRemoteDatasource = dataSource
        )

        val result = repository.searchProducts(
            query = "Apple",
            page = 1
        )

        assertTrue(result is AppResult.Error)

        val error = result as AppResult.Error
        assertEquals(ErrorMessages.SERVER, error.message)
        assertEquals(ErrorType.SERVER, error.type)
    }
}