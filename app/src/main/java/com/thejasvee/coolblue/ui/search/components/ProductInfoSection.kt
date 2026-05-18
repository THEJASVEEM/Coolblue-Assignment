package com.thejasvee.coolblue.ui.search.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.thejasvee.coolblue.domain.model.Product
import com.thejasvee.coolblue.ui.theme.CoolblueRadius
import com.thejasvee.coolblue.ui.theme.CoolblueSpacing
import com.thejasvee.coolblue.ui.theme.OrangePrimary
import com.thejasvee.coolblue.ui.theme.RatingStarColor
import com.thejasvee.coolblue.ui.theme.Success
import com.thejasvee.coolblue.ui.theme.mutedTextColor

@Composable
fun ProductInfoSection(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = product.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(10.dp))

        product.usps.take(3).forEach { usp ->
            UspRow(text = usp)
        }

        product.productReviewSummary?.let { reviewSummary ->
            Spacer(modifier = Modifier.height(6.dp))

            RatingRow(
                average = reviewSummary.average,
                count = reviewSummary.count
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        ListPriceText(
            listPrice = product.listPrice,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatPrice(product.price),
                style = MaterialTheme.typography.titleMedium,
                color = OrangePrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            if (product.nextDayDelivery) {
                DeliveryBadge()
            }
        }
    }

}

@Composable
private fun UspRow(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(top = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.CheckCircleOutline,
            contentDescription = null,
            tint = Success,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = text,
            modifier = Modifier.padding(start = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun RatingRow(
    average: Double,
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = RatingStarColor,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = " ${formatRating(average)}  ($count)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DeliveryBadge(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(CoolblueRadius.Md),
        color = Color.Transparent,
        border = BorderStroke(
            width = 1.dp,
            color = Success
        )
    ) {
        Row(
            modifier = modifier
                .padding(
                    vertical = CoolblueSpacing.Xs,
                    horizontal = CoolblueSpacing.Sm,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.LocalShipping,
                contentDescription = null,
                tint = Success,
                modifier = Modifier.size(14.dp)
            )

            Text(
                text = "Tomorrow",
                modifier = Modifier.padding(
                    horizontal = CoolblueSpacing.Sm,
                    vertical = 0.dp
                ),
                style = MaterialTheme.typography.labelSmall,
                color = Success,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ListPriceText(
    listPrice: Double?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        if (listPrice != null) {
            Text(
                text = formatPrice(listPrice),
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration = TextDecoration.LineThrough
                ),
                color = mutedTextColor()
            )
        }
    }
}

private fun formatPrice(price: Double): String {
    return if (price % 1.0 == 0.0) {
        "€ ${price.toInt()}"
    } else {
        "€ %.2f".format(price)
    }
}

private fun formatRating(rating: Double): String {
    return "%.1f".format(rating)
}