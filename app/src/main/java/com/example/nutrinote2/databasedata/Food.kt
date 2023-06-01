package com.example.nutrinote2.databasedata

data class Food(
    val id: Int,
    val name: String,
    val category: String,
    val carbs: Float,
    val protein: Float,
    val fat: Float
)