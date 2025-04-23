package com.example.yeschat.auth.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpScreenViewModel(): ViewModel() {
    private val _state: MutableStateFlow<SignUpScreenState> = MutableStateFlow(SignUpScreenState());
    val state: StateFlow<SignUpScreenState> = _state.asStateFlow();

    fun onEvent(event: SignUpScreenEvent) {
        when(event) {
            is SignUpScreenEvent.Name -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is SignUpScreenEvent.Email -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        validEmail = isValidEmail(event.email),
                        emailTouched = true
                    )
                }
            }
            is SignUpScreenEvent.Password -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        validPassword = isValidPassword(event.password),
                        passwordTouched = true
                    )
                }
            }
            is SignUpScreenEvent.SignUp -> {
                _state.update {
                    it.copy(
                        signUpButton = true
                    )
                };
                signUp(name = _state.value.name, email = _state.value.email, password = _state.value.password);
            }
        }
    }

    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isValidPassword(password: String): Boolean {
        val passwordValidator: Regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")
        return password.matches(passwordValidator)
    }

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true,
                    error = null
                )
            };
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _state.update {
                        it.copy(
                            loading = false,
                            signUpSuccess = true
                        )
                    };
                    it.user?.updateProfile(
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                    )
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