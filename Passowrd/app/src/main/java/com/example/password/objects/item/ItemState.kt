package com.example.password.objects.item

data class ItemState(
    val items: List<Item> = emptyList(),
    val name: String = "",
    val userName: String = "",
    val password: String = "",
    val info: String = "",
    val webLink: String = "",
    val isAddingItem: Boolean = false,
    val editingItem: Item? = null
)
