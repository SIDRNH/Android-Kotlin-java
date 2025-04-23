package com.example.yeschat.auth.login

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val loginButton: Boolean = false,
    val loading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null
)
