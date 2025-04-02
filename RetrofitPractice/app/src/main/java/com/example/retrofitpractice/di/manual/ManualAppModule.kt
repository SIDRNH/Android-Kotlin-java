package com.example.retrofitpractice.di.manual

import androidx.lifecycle.ViewModelProvider
import com.example.retrofitpractice.authentication.presentation.AuthViewModel
import com.example.retrofitpractice.homescreen.data.remote.RetrofitInstance
import com.example.retrofitpractice.homescreen.data.remote.api.WeatherApi
import com.example.retrofitpractice.homescreen.domain.WeatherRepository
import com.example.retrofitpractice.homescreen.presentation.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth

interface ManualAppModule {
    //Authentication
    val firebaseAuth: FirebaseAuth;
    val authViewModelFactory: ViewModelProvider.Factory;
    //Weather Home Screen
    val weatherApi: WeatherApi;
    val weatherRepository: WeatherRepository;
    val homeScreenViewModelFactory: ViewModelProvider.Factory;
}

class ManualAppModuleImpl: ManualAppModule {

    //Authentication
    override val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance();
    }

    override val authViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            AuthViewModel(auth = firebaseAuth);
        }

    //Home Screen
    override val weatherApi: WeatherApi by lazy {
        RetrofitInstance.weatherApi;
    }

    override val weatherRepository: WeatherRepository
        get() = WeatherRepository(weatherApi);

    override val homeScreenViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            HomeScreenViewModel(weatherRepository = weatherRepository, auth = firebaseAuth);
        }
}