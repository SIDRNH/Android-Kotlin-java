package com.example.retrofitpractice.authentication.data

data class UserEntity(
    val providerId: String = "",
    val uid: String = "",
    val name: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val isEmailVerified: Boolean = false
)
