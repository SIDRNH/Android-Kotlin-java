package com.example.password.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.password.objects.category.Category
import com.example.password.objects.item.Item

@Database(entities = [Category::class, Item::class], version = 2)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract val dao: Dao
}