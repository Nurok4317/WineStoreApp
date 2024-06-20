package com.example.winestoreapp.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.winestoreapp.data.Wine
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun WineDetailScreen(navController: NavHostController, wineId: String) {
    var wine by remember { mutableStateOf<Wine?>(null) }

    // Fetch wine details from Firestore
    LaunchedEffect(wineId) {
        val db = FirebaseFirestore.getInstance()
        db.collection("wines").document(wineId).get()
            .addOnSuccessListener { document ->
                wine = document.toObject(Wine::class.java)
            }
    }

    wine?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(it.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$${it.price}", color = Color.Green, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* TODO: Add to cart */ }) {
                Text("Add to Cart")
            }
        }
    }
}
