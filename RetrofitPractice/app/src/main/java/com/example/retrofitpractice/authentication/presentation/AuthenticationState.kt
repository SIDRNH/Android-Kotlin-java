package com.example.retrofitpractice.authentication.presentation

import com.example.retrofitpractice.authentication.presentation.model.Country
import com.example.retrofitpractice.authentication.presentation.model.countries

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
    val sentOtp: Boolean = false,
    val selectedOption: Country = countries.find { it.code == "IN" } ?: countries.first(),
    val limit: Int = countries.find { it.code == selectedOption.code }?.limit ?: countries.first().limit,
    val verificationId: String = "",
    val isLoading: Boolean = false,
    val loggedIn: Boolean = false
)
