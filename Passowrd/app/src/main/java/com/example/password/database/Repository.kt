package com.example.password.database

import com.example.password.objects.category.Category
import com.example.password.objects.item.Item
import kotlinx.coroutines.flow.Flow

class Repository(private val dao: Dao) {

    //Category
    suspend fun insertCategory(category: Category) = dao.insertCategory(category);
    suspend fun updateCategory(category: Category) = dao.updateCategory(category);
    suspend fun deleteCategory(category: Category) = dao.deleteCategory(category);
    fun getCategories(): Flow<List<Category>> = dao.getCategories();

    //Item
    suspend fun insertItem(item: Item) = dao.insertItem(item);
    suspend fun updateItem(item: Item) = dao.updateItem(item);
    suspend fun deleteItem(item: Item) = dao.deleteItem(item);
    fun getItem(id: Int): Flow<List<Item>> = dao.getItem(id);

}