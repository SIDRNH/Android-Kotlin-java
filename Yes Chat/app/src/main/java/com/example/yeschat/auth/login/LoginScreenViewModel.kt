package com.example.yeschat.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
                        loginButton = true,
                    )
                };
                login(email = _state.value.email, password = _state.value.password);
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

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true
                )
            };
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _state.update {
                        it.copy(
                            loading = false,
                            loginSuccess = true
                        )
                    };
                }
                .addOnFailureListener { e ->
                    _state.update {
                        it.copy(
                            loading = false,
                            error = e.localizedMessage ?: "Something went wrong"
                        )
                    };
                }
        }
    }
}