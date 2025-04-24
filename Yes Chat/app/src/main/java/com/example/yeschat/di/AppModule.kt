package com.example.yeschat.di

import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

interface AppModule {
    //Authentication
    val firebaseAuth: FirebaseAuth;
    val loginScreenViewModelFactory: ViewModelProvider.Factory;
    val signUpScreenViewModelFactory: ViewModelProvider.Factory;

    //HomeScreen
    val firebaseDatabase: FirebaseDatabase;
    val homeScreenViewModelFactory: ViewModelProvider.Factory;

    //ChatScreen
    val chatScreenViewModelFactory: ViewModelProvider.Factory;
}