package com.example.winestoreapp.data

import com.example.winestoreapp.model.CartItem

data class Order(
    val id: String = "",
    val userId: String = "",
    val items: List<CartItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val deliveryAddress: String = "",
    val status: String = "Pending"
)
