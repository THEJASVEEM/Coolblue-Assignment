package com.thejasvee.coolblue.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val productId: Int = 0,
    val productName: String,
    val reviewInformation: ReviewInformationDto? = null,

    @SerialName("USPs")
    val usps: List<String> = emptyList(),
    val availabilityState: Int = 0,
    val salesPriceIncVat: Double = 0.0,
    val listPriceIncVat: Double? = null,
    val listPriceExVat: Double? = null,
    val productImage: String = "",
    val coolbluesChoiceInformationTitle: String? = null,
    val promoIcon: PromoIconDto? = null,
    val nextDayDelivery: Boolean = false
)
