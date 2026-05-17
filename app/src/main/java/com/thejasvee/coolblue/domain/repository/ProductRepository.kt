package com.thejasvee.coolblue.domain.repository

import com.thejasvee.coolblue.core.result.AppResult
import com.thejasvee.coolblue.domain.model.ProductSearchResult

interface ProductRepository {

    suspend fun searchProducts(
        query: String,
        page: Int
    ): AppResult<ProductSearchResult>
}