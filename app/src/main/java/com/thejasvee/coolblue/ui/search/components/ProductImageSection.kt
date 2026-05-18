package com.thejasvee.coolblue.ui.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.thejasvee.coolblue.domain.model.Product
import com.thejasvee.coolblue.domain.model.ProductPromoType
import com.thejasvee.coolblue.ui.theme.CoolblueSpacing

@Composable
fun ProductImageSection(
    modifier: Modifier = Modifier,
    product: Product
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color.White)
            .clipToBounds()
    ) {
        if (product.imageUrl != null) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth(0.80f)
                    .height(300.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No image",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        product.promo?.let { promo ->
            PromoBadge(
                text = promo.text,
                type = promo.type,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(CoolblueSpacing.Sm)
            )
        }
    }
}

@Composable
private fun PromoBadge(
    text: String,
    type: ProductPromoType,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (type) {
        ProductPromoType.CoolbluesChoice -> MaterialTheme.colorScheme.primary
        ProductPromoType.ActionPrice -> MaterialTheme.colorScheme.secondary
        ProductPromoType.Unknown -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = CoolblueSpacing.Sm,
                vertical = CoolblueSpacing.Xs
            ),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}