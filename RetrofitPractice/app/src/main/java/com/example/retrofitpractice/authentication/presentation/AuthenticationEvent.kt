package com.example.retrofitpractice.authentication.presentation

sealed interface AuthenticationEvent {
    data object CCDClicked: AuthenticationEvent;
    data class SendOtp(val phoneNumber: String): AuthenticationEvent;
    data class PhoneNumber(val phoneNumber: String): AuthenticationEvent;
}