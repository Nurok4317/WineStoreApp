package com.example.winestoreapp.model

import androidx.lifecycle.ViewModel
import com.example.winestoreapp.data.Order
import com.example.winestoreapp.data.Wine
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    fun placeOrder(order: Order) {
        val currentOrders = _orders.value.toMutableList()
        currentOrders.add(order)
        _orders.value = currentOrders

        // Save order to Firestore
        val db = FirebaseFirestore.getInstance()
        db.collection("orders").add(order)
    }
        private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
        val CartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

        fun addToCart(wine: Wine, quantity: Int = 1) {
            val existingItem = _cartItems.value.find { it.wine.id == wine.id }
            if (existingItem != null) {
                // Update quantity if item already exists
                val updatedItems = _cartItems.value.map {
                    if (it.wine.id == wine.id) it.copy(quantity = it.quantity + quantity) else it
                }
                _cartItems.value = updatedItems
            } else {
                // Add new item to cart
                _cartItems.value = _cartItems.value + CartItem(wine, quantity)
            }
        }

        fun removeFromCart(wine: Wine) {
            _cartItems.value = _cartItems.value.filter { it.wine.id != wine.id }
        }

        fun updateQuantity(wine: Wine, newQuantity: Int) {
            if (newQuantity > 0) {
                _cartItems.value = _cartItems.value.map {
                    if (it.wine.id == wine.id) it.copy(quantity = newQuantity) else it
                }
            } else {
                removeFromCart(wine)
            }
        }
    }

