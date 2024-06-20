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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.winestoreapp.model.CartViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CatalogueScreen(navController: NavHostController, cartViewModel: CartViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val wines = remember { mutableStateListOf<Wine>() }

    // Fetch wines from Firestore
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("wines").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val wine = document.toObject(Wine::class.java)
                    wines.add(wine)
                }
            }
    }

    Column {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search wines") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        LazyColumn {
            items(wines.filter { it.name.contains(searchQuery, true) }) { wine ->
                WineItem(wine) {
                    navController.navigate("wineDetails/${wine.id}")
                }
            }
        }
    }
}

@Composable
fun WineItem(wine: Wine, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberImagePainter(wine.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = wine.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = wine.description, style = MaterialTheme.typography.bodySmall)
            Text(text = "$${wine.price}", color = Color.Green, fontSize = 16.sp)
        }
    }
}
