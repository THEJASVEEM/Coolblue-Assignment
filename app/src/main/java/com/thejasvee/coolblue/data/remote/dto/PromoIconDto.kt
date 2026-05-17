package com.thejasvee.coolblue.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PromoIconDto(
    val text: String = "",
    val type: String = "",
)
