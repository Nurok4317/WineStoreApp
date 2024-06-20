package com.example.winestoreapp.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.winestoreapp.data.Promotion

@Composable
fun PromotionItem(promotion: Promotion, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(promotion.imageUrl),
                contentDescription = promotion.title,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = promotion.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = promotion.description, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Valid until: ${promotion.endDate}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
