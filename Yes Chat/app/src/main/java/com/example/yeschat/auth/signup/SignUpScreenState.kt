package com.example.yeschat.auth.signup

data class SignUpScreenState(
    val email: String = "",
    val password: String = "",
    val signUpButton: Boolean = false,
    val validEmail: Boolean = false,
    val validPassword: Boolean = false,
    val emailTouched: Boolean = false,
    val passwordTouched: Boolean = false
)
