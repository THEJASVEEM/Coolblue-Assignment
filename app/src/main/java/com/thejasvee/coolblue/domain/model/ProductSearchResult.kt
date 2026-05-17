package com.thejasvee.coolblue.domain.model

data class ProductSearchResult(
    val products: List<Product>,
    val currentPage: Int,
    val pageSize: Int,
    val totalResults: Int,
    val pageCount: Int,
) {
    companion object {
        fun empty(): ProductSearchResult {
            return ProductSearchResult(
                products = emptyList(),
                currentPage = 0,
                pageSize = 0,
                totalResults = 0,
                pageCount = 0
            )
        }
    }
}

