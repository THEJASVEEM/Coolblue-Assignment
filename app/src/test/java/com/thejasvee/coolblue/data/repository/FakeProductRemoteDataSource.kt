package com.thejasvee.coolblue.data.repository

import com.thejasvee.coolblue.data.remote.datasource.ProductRemoteDataSource
import com.thejasvee.coolblue.data.remote.dto.SearchProductResponseDto

class FakeProductRemoteDataSource(
    private val response: SearchProductResponseDto? = null,
    private val exception: Exception? = null

) : ProductRemoteDataSource {
    override suspend fun searchProducts(
        query: String,
        page: Int
    ): SearchProductResponseDto {
        exception?.let { throw it }
        return requireNotNull(response)
    }
}