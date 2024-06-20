package com.example.winestoreapp.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.winestoreapp.data.Promotion
import com.example.winestoreapp.data.Wine

@Composable
fun WineItem(wine: Wine, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberImagePainter(wine.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = wine.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "$${wine.price}", color = Color.Green, fontSize = 14.sp)
        }
    }
}

@Composable
fun PromotionItem(promotion: Promotion, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberImagePainter(promotion.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = promotion.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "${promotion.startDate} - ${promotion.endDate}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
