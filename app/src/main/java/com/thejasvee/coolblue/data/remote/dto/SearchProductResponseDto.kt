package com.thejasvee.coolblue.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchProductResponseDto(
    val products: List<ProductDto> = emptyList(),
    val currentPage: Int = 0,
    val pageSize: Int = 0,
    val totalResults: Int = 0,
    val pageCount: Int = 0,
)
