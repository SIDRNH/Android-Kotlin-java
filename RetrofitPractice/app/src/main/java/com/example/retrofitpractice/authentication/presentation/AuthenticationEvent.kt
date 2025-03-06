package com.example.retrofitpractice.authentication.presentation

import android.app.Activity

sealed interface AuthenticationEvent {
    data object CCDClicked: AuthenticationEvent;
    data class SendOtp(val activity: Activity, val phoneNumber: String): AuthenticationEvent;
    data class PhoneNumber(val phoneNumber: String): AuthenticationEvent;
    data class OnEnterNumber(val number: Int?, val index: Int): AuthenticationEvent
    data class OnChangeFieldFocused(val index: Int): AuthenticationEvent
    data object OnKeyboardBack: AuthenticationEvent
}