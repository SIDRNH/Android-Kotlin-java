package com.example.password.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.password.components.snackbar.SnackbarEvent
import com.example.password.components.snackbar.Snackbars
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {

    private val _authState: MutableLiveData<AuthState> = MutableLiveData<AuthState>();
    val authState: LiveData<AuthState> = _authState;
    var email: String by mutableStateOf("")
        private set;
    var password: String by mutableStateOf("")
        private set;

    init {
        checkAuthState();
    }

        fun checkAuthState() {
            if (auth.currentUser == null) _authState.value = AuthState.Unauthenticated("Please Login");
            else _authState.value = AuthState.Authenticated("Welcome Back");
        }

    fun onEmailChange(newEmail: String) {
        email = newEmail;
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword;
    }

    fun isFormValid(): Boolean {
        return email.isNotBlank() && password.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private fun updateAuthState(newState: AuthState) {
        _authState.value = newState;
        if (newState is AuthState.Error || newState is AuthState.Authenticated) {
            viewModelScope.launch {
                Snackbars.sendEvent(SnackbarEvent(newState.message));
            }
        }
    }

    fun login() {
        if (!isFormValid()) {
            updateAuthState(AuthState.Error("Email or Password can't be empty"));
            return;
        }
        _authState.value = AuthState.Loading;
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                viewModelScope.launch{
                    Snackbars.sendEvent(
                        event = SnackbarEvent(
                            message = "Logged in Successfully"
                        )
                    )
                }
                updateAuthState(AuthState.Authenticated("Logged in Successfully"));
            }
            .addOnFailureListener {
                handleFirebaseException(it);
            }
    }

    fun signup() {
        if (!isFormValid()) {
            updateAuthState(AuthState.Error("Email or Password can't be empty"))
            return;
        }
        _authState.value = AuthState.Loading;
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
               updateAuthState(AuthState.Authenticated("Account Created"));
                viewModelScope.launch{
                    Snackbars.sendEvent(SnackbarEvent(authState.value!!.message));
                }
            }
            .addOnFailureListener {
                handleFirebaseException(it);
            }
    }

    fun signOut() {
        auth.signOut();
        updateAuthState(AuthState.Unauthenticated("Signed Out Successfully"));
        viewModelScope.launch{
            Snackbars.sendEvent(SnackbarEvent(authState.value!!.message));
        }
    }

    private fun handleFirebaseException(e: Exception) {
        val errorMessage: String = when (e) {
            is FirebaseAuthInvalidUserException -> "Invalid email or password"
            is FirebaseAuthInvalidCredentialsException -> "Incorrect password"
            is FirebaseAuthUserCollisionException -> "User already exists"
            else -> e.message ?: "Something went wrong"
        }
        updateAuthState(AuthState.Error(errorMessage));
    }
}

sealed class AuthState(val message: String) {
    data class Authenticated(val successMessage: String): AuthState(successMessage);
    data class Unauthenticated(val info: String): AuthState(info);
    data class Error(val errorMessage: String): AuthState(errorMessage);
    object Loading: AuthState("");
}