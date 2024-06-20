package com.example.winestoreapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.winestoreapp.data.Promotion
import com.example.winestoreapp.data.Wine
import com.google.firebase.firestore.FirebaseFirestore
import com.example.winestoreapp.items.WineItem
import com.example.winestoreapp.items.PromotionItem

@Composable
fun HomeScreen(navController: NavHostController) {
    val popularWines = remember { mutableStateListOf<Wine>() }
    val promotions = remember { mutableStateListOf<Promotion>() }

    // Fetch popular wines and promotions from Firestore
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()

        db.collection("wines").whereEqualTo("popular", true).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val wine = document.toObject(Wine::class.java)
                    popularWines.add(wine)
                }
            }

        db.collection("promotions").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val promotion = document.toObject(Promotion::class.java)
                    promotions.add(promotion)
                }
            }
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text("Popular Wines", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow {
                items(popularWines) { wine ->
                    WineItem(wine) {
                        navController.navigate("wineDetails/${wine.id}")
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Current Promotions", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow {
                items(promotions) { promotion ->
                    PromotionItem(promotion) {
                        navController.navigate("promotionDetails/${promotion.id}")
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Wine Culture", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            VideoPlayer("https://www.example.com/video.mp4")
        }
    }
}



@Composable
fun VideoPlayer(url: String) {
    // Use ExoPlayer or any other video player library to play video
    AndroidView(factory = { context ->
        PlayerView(context).apply {
            player = ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(url))
                prepare()
                playWhenReady = false
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
