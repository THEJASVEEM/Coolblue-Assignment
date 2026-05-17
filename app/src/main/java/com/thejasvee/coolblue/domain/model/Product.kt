package com.thejasvee.coolblue.domain.model

data class Product(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val price: Double,
    val listPrice: Double?,
    val productReviewSummary: ProductReviewSummary?,
    val usps: List<String>,
    val promo: ProductPromo?,
    val nextDayDelivery: Boolean
)