package com.example.recipeapp

data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: List<Ingredient>,
    val method: String,
    val imageUrl: String,
)

