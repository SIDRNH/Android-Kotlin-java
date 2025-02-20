package com.example.retrofitpractice.domain

import com.example.retrofitpractice.BuildConfig
import com.example.retrofitpractice.data.remote.api.WeatherApi
import com.example.retrofitpractice.data.remote.model.CurrentWeatherResponse
import com.example.retrofitpractice.data.remote.model.SearchResponse
import retrofit2.Response

class WeatherRepository(private val weatherApi: WeatherApi) {

    private val weatherApiKey: String = BuildConfig.API_KEY;
    // Current Weather
    suspend fun getCurrentWeather(location: String): Response<CurrentWeatherResponse> = weatherApi.getCurrentWeather(key = weatherApiKey, city = location);

    //Search Api
    suspend fun searchQuery(location: String): Response<List<SearchResponse>> = weatherApi.searchQuery(key = weatherApiKey, city = location);
}