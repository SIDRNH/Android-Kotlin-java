package com.example.yeschat.auth.signup

data class SignUpScreenState(
    val email: String = "",
    val password: String = "",
    val signUpButton: Boolean = false
)
