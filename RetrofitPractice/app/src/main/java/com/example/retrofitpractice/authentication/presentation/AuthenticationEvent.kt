package com.example.retrofitpractice.authentication.presentation

import androidx.activity.ComponentActivity
import com.example.retrofitpractice.authentication.presentation.model.Country

sealed interface AuthenticationEvent {
    data object CCDExpanded: AuthenticationEvent;
    data class DropdownSelectedItem(val country: Country): AuthenticationEvent;
    data class SendOtp(val activity: ComponentActivity, val phoneNumber: String): AuthenticationEvent;
    data object VerifyOtp: AuthenticationEvent;
    data class PhoneNumber(val phoneNumber: String): AuthenticationEvent;
    data class Otp(val otp: String): AuthenticationEvent
}