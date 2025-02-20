package com.example.password.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.password.objects.category.Category
import com.example.password.objects.item.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    //Category Dao
    @Insert
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category);

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<Category>>

    //Item Dao
    @Insert
    suspend fun insertItem(item: Item);

    @Update
    suspend fun updateItem(item: Item);

    @Delete
    suspend fun deleteItem(item: Item);

    @Query("SELECT * FROM item WHERE category_id = :id")
    fun getItem(id: Int): Flow<List<Item>>
}