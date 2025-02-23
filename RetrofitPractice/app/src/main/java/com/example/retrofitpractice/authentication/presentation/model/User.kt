package com.example.retrofitpractice.authentication.presentation.model

data class User(
    val providerId: String,
    val uid: String,
    val name: String? = null,
    val phoneNumber: String,
    val email: String? = null,
    val password: String,
    val photoUrl: String? = null,
    val isEmailVerified: Boolean
)