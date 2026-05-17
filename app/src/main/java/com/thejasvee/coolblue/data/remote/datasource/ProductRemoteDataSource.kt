package com.thejasvee.coolblue.data.remote.datasource

import com.thejasvee.coolblue.data.remote.dto.SearchProductResponseDto

interface ProductRemoteDataSource {

    suspend fun searchProducts(
        query: String,
        page: Int,
    ): SearchProductResponseDto
}