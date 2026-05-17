package com.thejasvee.coolblue.data.remote.datasource

import com.thejasvee.coolblue.data.remote.api.ProductApi
import com.thejasvee.coolblue.data.remote.dto.SearchProductResponseDto
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val productApi: ProductApi
) : ProductRemoteDatasource {

    override suspend fun searchProducts(
        query: String,
        page: Int
    ): SearchProductResponseDto {
        return productApi.searchProducts(
            query = query,
            page = page
        )
    }
}