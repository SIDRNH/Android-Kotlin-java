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
    val isCCDExpanded: Boolean = false,
    val otp: String = "",
    val otpDialog: Boolean = false,
    val sentOtp: Boolean = false,
    val code: List<Int?> = (1..4).map { null },
    val focusedIndex: Int? = null,
    val isValid: Boolean? = null
)
