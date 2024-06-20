package com.example.winestoreapp.screens
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.winestoreapp.data.Wine

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiscountsScreen(navController: NavHostController, winesWithDiscount: List<Wine>) {
    Scaffold(
        topBar = {
            // Top app bar
        },
        bottomBar = {
            // Bottom navigation bar
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Discounts", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(winesWithDiscount) { wine ->
                    WineItem(wine) {
                        navController.navigate("wineDetails/${wine.id}")
                    }
                }
            }
        }
    }
}
