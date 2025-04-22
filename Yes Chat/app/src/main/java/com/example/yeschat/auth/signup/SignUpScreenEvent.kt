package com.example.yeschat.auth.signup

sealed interface SignUpScreenEvent {
    data class Email(val email: String): SignUpScreenEvent;
    data class Password(val password: String): SignUpScreenEvent;
    data object SignUp: SignUpScreenEvent;
}