// UserScreen.kt
package com.example.winestoreapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UserScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Text(text = "Welcome to the Wine Store")
            // Add more components to show wines, promotions, etc.
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    UserScreen()
}
