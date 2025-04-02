package com.example.retrofitpractice.authentication.presentation

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit

class AuthViewModel(private val auth: FirebaseAuth): ViewModel() {

    private val _state = MutableStateFlow(AuthenticationState());
    val state: StateFlow<AuthenticationState> = _state.asStateFlow();

    fun onEvent(event: AuthenticationEvent) {
        when(event) {
            AuthenticationEvent.CCDExpanded -> {
                _state.update {
                    it.copy(
                        isCCDExpanded = !_state.value.isCCDExpanded
                    )
                }
            }
            is AuthenticationEvent.DropdownSelectedItem -> {
                _state.update {
                    it.copy(
                        selectedOption = event.country,
                        limit = event.country.limit,
                        phoneNumber = "",
                    )
                }
            }
            is AuthenticationEvent.PhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }
            is AuthenticationEvent.SendOtp -> {
                _state.update {
                    it.copy(
                        isLoading = true,
                    )
                }
                sendOtp(activity = event.activity);
            }

            is AuthenticationEvent.Otp -> {
                _state.update {
                    it.copy(
                        otp = event.otp
                    )
                }
            }

            AuthenticationEvent.VerifyOtp -> {
                val credential = PhoneAuthProvider.getCredential(_state.value.verificationId, _state.value.otp)
                _state.update {
                    it.copy(
                        isLoading = true,
                    )
                }
                signInWithPhoneNumber(auth, credential)
            }
        }
    }

    private fun sendOtp(activity: ComponentActivity) {

        val callbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                signInWithPhoneNumber(auth = auth, credential = p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        sentOtp = false,
                        phoneNumber = ""
                    )
                }
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        sentOtp = true,
                        verificationId = p0
                    )
                }
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(_state.value.selectedOption.code + _state.value.phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneNumber(auth: FirebaseAuth, credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnSuccessListener {
            _state.update {
                it.copy(
                    isLoading = false,
                    otp = "",
                    loggedIn = true
                )
            }
        }.addOnFailureListener {
            _state.update {
                it.copy(
                    isLoading = false,
                    otp = ""
                )
            }
        }
    }
}