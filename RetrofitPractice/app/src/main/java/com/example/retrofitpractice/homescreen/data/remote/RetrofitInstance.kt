package com.example.retrofitpractice.homescreen.data.remote

import com.example.retrofitpractice.homescreen.data.remote.api.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.weatherapi.com"
    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    val weatherApi: WeatherApi = getInstance().create(WeatherApi::class.java);

}