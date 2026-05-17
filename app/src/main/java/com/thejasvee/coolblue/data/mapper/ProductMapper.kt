package com.thejasvee.coolblue.data.mapper

import com.thejasvee.coolblue.data.remote.dto.ProductDto
import com.thejasvee.coolblue.data.remote.dto.PromoIconDto
import com.thejasvee.coolblue.data.remote.dto.ReviewSummaryDto
import com.thejasvee.coolblue.data.remote.dto.SearchProductResponseDto
import com.thejasvee.coolblue.domain.model.Product
import com.thejasvee.coolblue.domain.model.ProductPromo
import com.thejasvee.coolblue.domain.model.ProductPromoType
import com.thejasvee.coolblue.domain.model.ProductReviewSummary
import com.thejasvee.coolblue.domain.model.ProductSearchResult


fun SearchProductResponseDto.toDomain(): ProductSearchResult {
    return ProductSearchResult(
        products = products.mapNotNull { it.toDomainOrNull() },
        currentPage = currentPage,
        pageSize = pageSize,
        totalResults = totalResults,
        pageCount = pageCount
    )
}

fun ProductDto.toDomainOrNull(): Product? {

    if (productId <= 0) return null

    if (productName.isBlank()) return null

    if (salesPriceIncVat <= 0.0) return null

    val imageUrl = productImage.takeIf { it.isNotBlank() }

    return Product(
        id = productId,
        name = productName,
        imageUrl = imageUrl,
        price = salesPriceIncVat,
        listPrice = listPriceIncVat,
        nextDayDelivery = nextDayDelivery,
        usps = usps,
        promo = promoIcon?.toDomain(),
        productReviewSummary = reviewInformation?.reviewSummary?.toDomain()
    )
}

fun PromoIconDto.toDomain(): ProductPromo {
    return ProductPromo(
        text = text,
        type = type.toProductPromoType(),
    )
}

private fun String.toProductPromoType(): ProductPromoType {
    return when (this) {
        "coolblues-choice" -> ProductPromoType.CoolbluesChoice
        "action-price" -> ProductPromoType.ActionPrice
        else -> ProductPromoType.Unknown
    }
}

fun ReviewSummaryDto.toDomain(): ProductReviewSummary {
    return ProductReviewSummary(
        average = reviewAverage,
        count = reviewCount,
    )
}