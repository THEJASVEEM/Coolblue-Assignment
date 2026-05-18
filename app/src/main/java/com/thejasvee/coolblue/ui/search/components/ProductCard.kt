package com.thejasvee.coolblue.ui.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thejasvee.coolblue.domain.model.Product
import com.thejasvee.coolblue.ui.theme.CoolblueSpacing

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CoolblueSpacing.Lg),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column {
            ProductImageSection(
                product = product
            )
            ProductInfoSection(
                product = product,
                modifier = Modifier.padding(CoolblueSpacing.Md)
            )
        }
    }
}