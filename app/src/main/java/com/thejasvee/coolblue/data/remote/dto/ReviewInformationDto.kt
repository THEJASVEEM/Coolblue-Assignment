package com.thejasvee.coolblue.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewInformationDto(
    val reviews: List<String> = emptyList(),
    val reviewSummary: ReviewSummaryDto? = null,
)
