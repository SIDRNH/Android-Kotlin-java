package com.example.yeschat.auth.login

sealed interface LoginScreenEvent {
    data class Email(val email: String): LoginScreenEvent;
    data class Password(val password: String): LoginScreenEvent;
    data object Login: LoginScreenEvent;
}