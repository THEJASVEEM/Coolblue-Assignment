package com.thejasvee.coolblue.domain.usecase

import com.thejasvee.coolblue.core.result.AppResult
import com.thejasvee.coolblue.domain.model.ProductSearchResult
import com.thejasvee.coolblue.domain.repository.ProductRepository

class FakeProductRepository(private val result: AppResult<ProductSearchResult>) :
    ProductRepository {

    var receivedQuery: String? = null
        private set

    var receivedPage: Int = 0
        private set

    var callCount: Int = 0
        private set

    override suspend fun searchProducts(
        query: String,
        page: Int
    ): AppResult<ProductSearchResult> {
        receivedPage = page
        receivedQuery = query
        callCount++
        return result
    }
}