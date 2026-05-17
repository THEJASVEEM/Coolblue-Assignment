package com.thejasvee.coolblue.data.mapper

import com.thejasvee.coolblue.data.remote.dto.PromoIconDto
import com.thejasvee.coolblue.domain.model.ProductPromoType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ProductMapperTest {

    @Test

    fun `valid product dto maps to domain`() {

        val dto = productDto()

        val result = dto.toDomainOrNull()

        val product = requireNotNull(result)
        assertEquals(785359, product.id)
        assertEquals("Apple iPhone 6 32GB Grijs", product.name)
        assertEquals(
            "https://image.coolblue.nl/300x750/products/818870",
            product.imageUrl
        )
        assertEquals(369.0, product.price, 0.0)
        assertEquals(9.1, product.productReviewSummary?.average ?: 0.0, 0.0)
        assertEquals(952, product.productReviewSummary?.count)
        assertEquals(2, product.usps.size)
        assertEquals("mini iPhone", product.promo?.text)
        assertEquals(
            ProductPromoType.CoolbluesChoice,
            product.promo?.type
        )
        assertEquals(true, product.nextDayDelivery)
    }

    @Test
    fun `invalid product dto id maps to null`() {

        val product = productDto(productId = 0)

        val result = product.toDomainOrNull()

        assertNull(result)
    }

    @Test
    fun `empty product name maps to null`() {

        val product = productDto(productName = "")

        val result = product.toDomainOrNull()

        assertNull(result)
    }

    @Test
    fun `blank product name maps to null`() {
        val product = productDto(productName = "   ")

        val result = product.toDomainOrNull()

        assertNull(result)
    }

    @Test
    fun `invalid product salePrice maps to null`() {

        val product = productDto(salesPriceIncVat = 0.0)

        val result = product.toDomainOrNull()

        assertNull(result)
    }

    @Test
    fun `blank image url maps with null image url`() {
        val product = productDto(productImage = "")

        val result = product.toDomainOrNull()

        val mappedProduct = requireNotNull(result)
        assertNull(mappedProduct.imageUrl)
    }

    @Test
    fun `product with whitespace image url maps with null image url`() {
        val product = productDto(productImage = "   ")

        val result = product.toDomainOrNull()

        val resProduct = requireNotNull(result)
        assertNull(resProduct.imageUrl)
    }

    @Test
    fun `product without promo icon maps promo as null`() {
        val product = productDto(promoIcon = null)

        val result = product.toDomainOrNull()

        val mappedProduct = requireNotNull(result)
        assertNull(mappedProduct.promo)
    }


    @Test
    fun `coolblues choice promo type maps correctly`() {
        val productDto =
            productDto(
                promoIcon = PromoIconDto(
                    "mini ipad",
                    type = "coolblues-choice"
                )
            )

        val result = productDto.toDomainOrNull()

        val product = requireNotNull(result)
        val promo = requireNotNull(product.promo)
        assertEquals(ProductPromoType.CoolbluesChoice, promo.type)
    }

    @Test
    fun `action price promo type maps correctly`() {
        val productDto = productDto(
            promoIcon = PromoIconDto(
                text = "aanbieding",
                type = "action-price"
            )
        )

        val result = productDto.toDomainOrNull()

        val product = requireNotNull(result)
        val promo = requireNotNull(product.promo)

        assertEquals(ProductPromoType.ActionPrice, promo.type)
    }

    @Test
    fun `unknown promo type maps to unknown`() {
        val productDto = productDto(
            promoIcon = PromoIconDto(
                "mini ipad",
                type = "new-promo"
            )
        )

        val result = productDto.toDomainOrNull()

        val product = requireNotNull(result)
        val promo = requireNotNull(product.promo)
        assertEquals(ProductPromoType.Unknown, promo.type)
    }

    @Test
    fun `response mapper filters invalid products`() {
        val response = searchProductResponseDto(
            products = listOf(
                productDto(productId = 1),
                productDto(productId = 0),
                productDto(productName = ""),
                productDto(salesPriceIncVat = 0.0)
            )
        )

        val result = response.toDomain()

        assertEquals(1, result.products.size)
        assertEquals(1, result.products.first().id)
    }

    @Test
    fun `response mapper keeps pagination metadata`() {
        val response = searchProductResponseDto(
            currentPage = 2,
            pageSize = 24,
            totalResults = 70,
            pageCount = 3
        )

        val result = response.toDomain()

        assertEquals(2, result.currentPage)
        assertEquals(24, result.pageSize)
        assertEquals(70, result.totalResults)
        assertEquals(3, result.pageCount)
    }
}
