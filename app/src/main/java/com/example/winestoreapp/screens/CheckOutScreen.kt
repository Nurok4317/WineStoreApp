package com.example.winestoreapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.winestoreapp.data.Order
import com.example.winestoreapp.model.CartViewModel
import com.example.winestoreapp.model.OrderViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun CheckoutScreen(navController: NavHostController, cartViewModel: CartViewModel, orderViewModel: OrderViewModel, user: FirebaseUser) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    var deliveryAddress by remember { mutableStateOf("") }
    val totalAmount = cartItems.sumOf { it.wine.price * it.quantity }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Checkout", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(cartItems) { cartItem ->
                CartItemRow(cartItem, cartViewModel::updateQuantity, cartViewModel::removeFromCart)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = deliveryAddress,
            onValueChange = { deliveryAddress = it },
            label = { Text("Delivery Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Total: $${totalAmount}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val order = Order(
                    userId = user.uid,
                    items = cartItems,
                    totalAmount = totalAmount,
                    deliveryAddress = deliveryAddress
                )
                orderViewModel.placeOrder(order)
                navController.navigate("orders")
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Place Order")
        }
    }
}
