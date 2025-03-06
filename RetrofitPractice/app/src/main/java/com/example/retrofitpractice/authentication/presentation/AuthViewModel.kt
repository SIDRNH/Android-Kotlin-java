package com.example.retrofitpractice.authentication.presentation

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit

private const val VALID_OTP_CODE = "1414";

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
                sendOtp(event.activity, phoneNumber);
                _state.update {
                    it.copy(
                        sentOtp = true
                    );
                }
            }

            is AuthenticationEvent.PhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    );
                }
            }

            is AuthenticationEvent.OnChangeFieldFocused -> {
                _state.update { it.copy(
                    focusedIndex = event.index
                ) }
            }
            is AuthenticationEvent.OnEnterNumber -> {
                enterNumber(event.number, event.index)
            }
            AuthenticationEvent.OnKeyboardBack -> {
                val previousIndex = getPreviousFocusedIndex(state.value.focusedIndex)
                _state.update { it.copy(
                    code = it.code.mapIndexed { index, number ->
                        if(index == previousIndex) {
                            null
                        } else {
                            number
                        }
                    },
                    focusedIndex = previousIndex
                ) }
            }
        }
    }

    private val phoneNumber: String = "+916789457245";
    private val otp: String = "675432";
    //val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(phoneNumber, otp);

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener{task ->
                Log.d("Sign In", "signInWithCredential:success")
                val user = task.user
            }
            .addOnFailureListener {
                Log.w("Sign In", "signInWithCredential:failure", it)
                if (it is FirebaseAuthInvalidCredentialsException) {
                    Log.d("Invalid Credential", "The verification code entered was invalid");
                }
            }
    }

    fun sendOtp(activity: Activity, phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private val callbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("Send Otp", "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("Send Otp", "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d("Invalid Request", "Invalid request")
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                Log.d("Quota Finished", "The SMS quota for the project has been exceeded")
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                Log.d("Activity Missing", "reCAPTCHA verification attempted with null Activity")
                // reCAPTCHA verification attempted with null Activity
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("Code Sent", "onCodeSent:$verificationId");

            // Save verification ID and resending token so we can use them later
            //storedVerificationId = verificationId
            //resendToken = token
        }
    }


    private fun enterNumber(number: Int?, index: Int) {
        val newCode = state.value.code.mapIndexed { currentIndex, currentNumber ->
            if(currentIndex == index) {
                number
            } else {
                currentNumber
            }
        }
        val wasNumberRemoved = number == null
        _state.update { it.copy(
            code = newCode,
            focusedIndex = if(wasNumberRemoved || it.code.getOrNull(index) != null) {
                it.focusedIndex
            } else {
                getNextFocusedTextFieldIndex(
                    currentCode = it.code,
                    currentFocusedIndex = it.focusedIndex
                )
            },
            isValid = if(newCode.none { it == null }) {
                newCode.joinToString("") == VALID_OTP_CODE
            } else null
        ) }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?
    ): Int? {
        if(currentFocusedIndex == null) {
            return null
        }

        if(currentFocusedIndex == 3) {
            return currentFocusedIndex
        }

        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int
    ): Int {
        code.forEachIndexed { index, number ->
            if(index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if(number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }
}