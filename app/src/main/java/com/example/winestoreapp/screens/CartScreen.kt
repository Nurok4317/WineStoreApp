package com.example.winestoreapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.winestoreapp.data.Wine
import com.example.winestoreapp.model.CartItem
import com.example.winestoreapp.model.OrderViewModel


@Composable
fun CartScreen(navController: NavHostController, cartViewModel: OrderViewModel) {
    val cartItems by cartViewModel.CartItems.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Cart", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(cartItems) { cartItem ->
                CartItemRow(cartItem, cartViewModel::updateQuantity, cartViewModel::removeFromCart)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: Handle checkout */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Proceed to Checkout")
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    updateQuantity: (Wine, Int) -> Unit,
    removeFromCart: (Wine) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(cartItem.wine.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(cartItem.wine.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("${cartItem.wine.price}$", color = Color.Green, fontSize = 16.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { updateQuantity(cartItem.wine, cartItem.quantity - 1) }) {
                    Icon(Icons.Default.Clear, contentDescription = null)
                }
                Text(cartItem.quantity.toString(), fontSize = 16.sp)
                IconButton(onClick = { updateQuantity(cartItem.wine, cartItem.quantity + 1) }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        }
        IconButton(onClick = { removeFromCart(cartItem.wine) }) {
            Icon(Icons.Default.Delete, contentDescription = "Remove from cart", tint = Color.Red)
        }
    }
}
