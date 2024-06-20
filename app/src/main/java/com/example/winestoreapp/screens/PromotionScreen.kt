package com.example.winestoreapp.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.winestoreapp.data.Promotion
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PromotionsScreen(navController: NavHostController) {
    val promotions = remember { mutableStateListOf<Promotion>() }

    // Fetch promotions from Firestore
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("promotions").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val promotion = document.toObject(Promotion::class.java)
                    promotions.add(promotion)
                }
            }
    }

    LazyColumn {
        items(promotions) { promotion ->
            PromotionItem(promotion) {
                navController.navigate("promotionDetails/${promotion.id}")
            }
        }
    }
}

@Composable
fun PromotionItem(promotion: Promotion, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberImagePainter(promotion.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = promotion.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "${promotion.startDate} - ${promotion.endDate}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
