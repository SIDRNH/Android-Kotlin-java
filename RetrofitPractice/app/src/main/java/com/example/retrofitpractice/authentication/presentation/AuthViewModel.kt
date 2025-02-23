package com.example.retrofitpractice.authentication.presentation

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel(private val auth: FirebaseAuth): ViewModel() {

    private val _state = MutableStateFlow(AuthenticationState());
    val state: StateFlow<AuthenticationState> = _state.asStateFlow();

    fun onEvent(event: AuthenticationEvent) {
        when(event) {
            AuthenticationEvent.CCDClicked -> {
                _state.update {
                    it.copy(
                        isCCDExpanded = true
                    );
                }
            }

            is AuthenticationEvent.SendOtp -> {

            }

            is AuthenticationEvent.PhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    );
                }
            }
        }
    }
}