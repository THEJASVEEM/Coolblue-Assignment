package com.thejasvee.coolblue.data.mapper

import com.thejasvee.coolblue.data.remote.dto.ProductDto
import com.thejasvee.coolblue.data.remote.dto.PromoIconDto
import com.thejasvee.coolblue.data.remote.dto.ReviewInformationDto
import com.thejasvee.coolblue.data.remote.dto.ReviewSummaryDto
import com.thejasvee.coolblue.data.remote.dto.SearchProductResponseDto


fun productDto(
    productId: Int = 785359,
    productName: String = "Apple iPhone 6 32GB Grijs",
    productImage: String = "https://image.coolblue.nl/300x750/products/818870",
    salesPriceIncVat: Double = 369.0,
    listPriceIncVat: Double? = null,
    reviewInformation: ReviewInformationDto? = reviewInformationDto(),
    usps: List<String> = listOf(
        "32 GB opslagcapaciteit",
        "4,7 inch Retina HD scherm"
    ),
    promoIcon: PromoIconDto? = promoIconDto(),
    nextDayDelivery: Boolean = true
): ProductDto {
    return ProductDto(
        productId = productId,
        productName = productName,
        productImage = productImage,
        salesPriceIncVat = salesPriceIncVat,
        listPriceIncVat = listPriceIncVat,
        reviewInformation = reviewInformation,
        usps = usps,
        promoIcon = promoIcon,
        nextDayDelivery = nextDayDelivery
    )
}

fun reviewInformationDto(reviewSummary: ReviewSummaryDto? = reviewSummaryDto()): ReviewInformationDto {
    return ReviewInformationDto(
        reviewSummary = reviewSummary,
    )
}

fun reviewSummaryDto(
    reviewAverage: Double = 9.1,
    reviewCount: Int = 952
): ReviewSummaryDto {
    return ReviewSummaryDto(
        reviewAverage = reviewAverage,
        reviewCount = reviewCount
    )
}

fun promoIconDto(
    text: String = "mini iPhone",
    type: String = "coolblues-choice"
): PromoIconDto {
    return PromoIconDto(
        text = text,
        type = type
    )
}


fun searchProductResponseDto(
    products: List<ProductDto> = emptyList(),
    currentPage: Int = 2,
    pageSize: Int = 24,
    totalResults: Int = 70,
    pageCount: Int = 3
): SearchProductResponseDto {
    return SearchProductResponseDto(
        products = products,
        currentPage = currentPage,
        pageSize = pageSize,
        totalResults = totalResults,
        pageCount = pageCount
    )
}