package com.example.yeschat.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.yeschat.auth.login.LoginScreenViewModel
import com.example.yeschat.auth.signup.SignUpScreenViewModel
import com.google.firebase.auth.FirebaseAuth

class AppModuleImpl(private val appContext: Context): AppModule {
    //Authentication
    override val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance();
    }
    override val loginScreenViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            LoginScreenViewModel();
        }
    override val signUpScreenViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            SignUpScreenViewModel();
        }
}