package com.example.password.objects.category

data class CategoryState (
    val categories: List<Category> = emptyList(),
    val name: String = "",
    val isAddingCategory: Boolean = false,
    val editingCategory: Category? = null
)