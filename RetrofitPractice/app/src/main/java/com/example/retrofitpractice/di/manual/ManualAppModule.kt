package com.example.retrofitpractice.di.manual

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.retrofitpractice.authentication.presentation.AuthViewModel
import com.example.retrofitpractice.homescreen.data.remote.RetrofitInstance
import com.example.retrofitpractice.homescreen.data.remote.api.WeatherApi
import com.example.retrofitpractice.homescreen.domain.WeatherRepository
import com.example.retrofitpractice.homescreen.presentation.HomeScreenViewModel
import com.example.retrofitpractice.roomdb.UserDao
import com.example.retrofitpractice.roomdb.UserRepository
import com.example.retrofitpractice.roomdb.UsersRoomDatabase
import com.google.firebase.auth.FirebaseAuth

interface ManualAppModule {
    //Authentication
    val firebaseAuth: FirebaseAuth;
    val authViewModelFactory: ViewModelProvider.Factory;
    //RoomDB
    val usersRoomDatabase: UsersRoomDatabase
    val userDao: UserDao
    val userRepository: UserRepository

    //Weather Home Screen
    val weatherApi: WeatherApi;
    val weatherRepository: WeatherRepository;
    val homeScreenViewModelFactory: ViewModelProvider.Factory;
}

/*
* we use by lazy {} when we need the value to be in Singleton
* we use get() when we need to create a new instance every time
 */

class ManualAppModuleImpl(private val appContext: Context): ManualAppModule {

    //Authentication
    override val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance();
    }

    override val authViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            AuthViewModel(auth = firebaseAuth, userRepository = userRepository);
        }

    //RoomDB
    override val usersRoomDatabase: UsersRoomDatabase by lazy {
        Room.databaseBuilder(
            context = appContext,
            klass = UsersRoomDatabase::class.java,
            name = "usersRoomDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    override val userDao: UserDao by lazy {
        usersRoomDatabase.userDao()
    }

    override val userRepository: UserRepository by lazy {
        UserRepository(userDao = userDao)
    }


    //Home Screen
    override val weatherApi: WeatherApi by lazy {
        RetrofitInstance.weatherApi;
    }

    override val weatherRepository: WeatherRepository
        get() = WeatherRepository(weatherApi);

    override val homeScreenViewModelFactory: ViewModelProvider.Factory
        get() = viewModelFactory {
            HomeScreenViewModel(weatherRepository = weatherRepository, auth = firebaseAuth, userRepository = userRepository);
        }
}