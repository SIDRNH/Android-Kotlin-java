package com.example.retrofitpractice.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val uid: String,
    val providerId: String? = null,
    val name: String? = "Guest",
    val phoneNumber: String? = "Not Available",
    val email: String? = null,
    val photoUrl: String? = null,
    val isEmailVerified: Boolean = false
)
