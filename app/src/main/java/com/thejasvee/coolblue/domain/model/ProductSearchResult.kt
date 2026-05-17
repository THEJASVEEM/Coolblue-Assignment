package com.thejasvee.coolblue.domain.model

data class ProductSearchResult(
    val products: List<Product>,
    val currentPage: Int,
    val pageSize: Int,
    val totalResults: Int,
    val pageCount: Int,
)

