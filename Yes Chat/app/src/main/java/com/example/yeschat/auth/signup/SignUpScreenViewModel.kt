package com.example.yeschat.auth.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpScreenViewModel: ViewModel() {
    private val _state: MutableStateFlow<SignUpScreenState> = MutableStateFlow(SignUpScreenState());
    val state: StateFlow<SignUpScreenState> = _state.asStateFlow();

    fun onEvent(event: SignUpScreenEvent) {
        when(event) {
            is SignUpScreenEvent.Email -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
            is SignUpScreenEvent.Password -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }
            SignUpScreenEvent.SignUp -> {
                _state.update {
                    it.copy(
                        signUpButton = true
                    )
                }
            }
        }
    }
}