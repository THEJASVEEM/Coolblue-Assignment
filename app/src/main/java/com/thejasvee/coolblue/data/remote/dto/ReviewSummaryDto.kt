package com.thejasvee.coolblue.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewSummaryDto(
    val reviewAverage: Double = 0.0,
    val reviewCount: Int = 0,
)
