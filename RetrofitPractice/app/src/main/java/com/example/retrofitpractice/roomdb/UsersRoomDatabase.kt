package com.example.retrofitpractice.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UsersRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}