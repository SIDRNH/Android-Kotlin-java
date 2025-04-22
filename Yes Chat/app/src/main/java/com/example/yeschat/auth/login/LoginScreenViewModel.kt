package com.example.yeschat.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenViewModel: ViewModel() {
    private val _state: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState());
    val state: StateFlow<LoginScreenState> = _state.asStateFlow();

    fun onEvent(event: LoginScreenEvent) {
        when(event) {
            is LoginScreenEvent.Email -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
            LoginScreenEvent.Login -> {
                _state.update {
                    it.copy(
                        loginButton = true
                    )
                }
                Log.d("Login", "${_state.value.loginButton}")
            }
            is LoginScreenEvent.Password -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }
        }
    }
}