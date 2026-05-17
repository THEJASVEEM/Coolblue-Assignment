package com.thejasvee.coolblue.data.repository

import com.thejasvee.coolblue.data.mapper.productDto
import com.thejasvee.coolblue.data.remote.dto.ProductDto
import com.thejasvee.coolblue.data.remote.dto.SearchProductResponseDto
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

fun searchProductResponseDto(
    products: List<ProductDto> = listOf(productDto()),
    currentPage: Int = 0,
    pageSize: Int = 0,
    totalResults: Int = 0,
    pageCount: Int = 0
): SearchProductResponseDto {
    return SearchProductResponseDto(
        products = products,
        currentPage = currentPage,
        pageSize = pageSize,
        totalResults = totalResults,
        pageCount = pageCount,
    )
}

fun httpException(): retrofit2.HttpException {
    val responseBody = "Server error"
        .toResponseBody("text/plain".toMediaType())

    return retrofit2.HttpException(
        retrofit2.Response.error<Any>(500, responseBody)
    )
}