package com.example.retrofitpractice.authentication.presentation

data class AuthenticationState(
    val providerId: String = "",
    val uid: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val photoUrl: String = "",
    val isEmailVerified: Boolean = false,
    val country: String = "",
    val countryCode: String = "",
    val countryIso: String ="",
    val isCCDExpanded: Boolean = false
)
