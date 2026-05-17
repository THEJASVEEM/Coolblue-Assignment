package com.thejasvee.coolblue.data.remote.api

import com.thejasvee.coolblue.data.remote.dto.SearchProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("search")
    suspend fun searchProducts(
        @Query("que") query: String,
        @Query("page") page: Int,
    ): SearchProductResponseDto

}