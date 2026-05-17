package com.thejasvee.coolblue.domain.usecase

import com.thejasvee.coolblue.core.result.AppResult
import com.thejasvee.coolblue.domain.model.ProductSearchResult
import com.thejasvee.coolblue.domain.repository.ProductRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(
        query: String,
        page: Int = DEFAULT_PAGE,
    ): AppResult<ProductSearchResult> {
        val trimmedQuery = query.trim()

        if (trimmedQuery.length < MIN_QUERY_LENGTH) return AppResult.Success(
            ProductSearchResult.empty()
        )

        return productRepository.searchProducts(
            query = trimmedQuery,
            page = page
        )
    }

    private companion object {
        const val MIN_QUERY_LENGTH = 2
        const val DEFAULT_PAGE = 1
    }
}