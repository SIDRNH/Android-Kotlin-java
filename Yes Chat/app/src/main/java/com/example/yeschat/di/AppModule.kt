package com.example.yeschat.di

import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

interface AppModule {
    //Authentication
    val firebaseAuth: FirebaseAuth;
    val loginScreenViewModelFactory: ViewModelProvider.Factory;
    val signUpScreenViewModelFactory: ViewModelProvider.Factory;
}