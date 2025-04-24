package com.example.yeschat.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.yeschat.auth.login.LoginScreenViewModel
import com.example.yeschat.auth.signup.SignUpScreenViewModel
import com.example.yeschat.chat.ChatScreenViewModel
import com.example.yeschat.home.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AppModuleImpl(private val appContext: Context): AppModule {
    //Authentication
    override val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance();
    }
    override val loginScreenViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            LoginScreenViewModel(firebaseAuth = firebaseAuth);
        }
    override val signUpScreenViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            SignUpScreenViewModel(firebaseAuth = firebaseAuth);
        }

    //HomeScreen
    override val firebaseDatabase: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
    override val homeScreenViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            HomeScreenViewModel(firebaseDatabase = firebaseDatabase);
        }

    //ChatScreen
    override val chatScreenViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            ChatScreenViewModel(firebaseDatabase = firebaseDatabase);
        }
}