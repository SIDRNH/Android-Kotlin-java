package com.example.retrofitpractice.di.manual

import androidx.lifecycle.ViewModelProvider
import com.example.retrofitpractice.data.remote.RetrofitInstance
import com.example.retrofitpractice.data.remote.api.WeatherApi
import com.example.retrofitpractice.domain.WeatherRepository
import com.example.retrofitpractice.presentation.homescreen.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth

interface ManualAppModule {
    val weatherApi: WeatherApi;
    val weatherRepository: WeatherRepository;
    val homeScreenViewModelFactory: ViewModelProvider.Factory;
    val firebaseAuth: FirebaseAuth;
}

class ManualAppModuleImpl: ManualAppModule {

    override val weatherApi: WeatherApi by lazy {
        RetrofitInstance.weatherApi;
    }

    override val weatherRepository: WeatherRepository
        get() = WeatherRepository(weatherApi);

    override val homeScreenViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            HomeScreenViewModel(weatherRepository = weatherRepository);
        }

    override val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance();
    }
}