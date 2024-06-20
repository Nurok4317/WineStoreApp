package com.example.winestoreapp.data


data class Wine(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val hasDiscount: Boolean = false,
    
    val imageUrl: String = ""
)
