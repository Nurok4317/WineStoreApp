package com.example.winestoreapp.model

import androidx.lifecycle.ViewModel
import com.example.winestoreapp.data.Wine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(wine: Wine, quantity: Int = 1) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.wine.id == wine.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            currentItems.add(CartItem(wine, quantity))
        }
        _cartItems.value = currentItems
    }

    fun removeFromCart(wine: Wine) {
        _cartItems.value = _cartItems.value.filter { it.wine.id != wine.id }
    }

    fun updateQuantity(wine: Wine, quantity: Int) {
        _cartItems.value = _cartItems.value.map {
            if (it.wine.id == wine.id) it.copy(quantity = quantity) else it
        }
    }
}

data class CartItem(val wine: Wine, var quantity: Int)
