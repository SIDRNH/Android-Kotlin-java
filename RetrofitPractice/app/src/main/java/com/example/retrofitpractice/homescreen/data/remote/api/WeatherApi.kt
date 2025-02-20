package com.example.retrofitpractice.homescreen.data.remote.api

import com.example.retrofitpractice.homescreen.data.remote.model.CurrentWeatherResponse
import com.example.retrofitpractice.homescreen.data.remote.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/current.json")
    suspend fun getCurrentWeather(@Query("key") key: String, @Query("q") city: String): Response<CurrentWeatherResponse>;

    @GET("/v1/search.json")
    suspend fun searchQuery(@Query("key") key: String, @Query("q") city: String): Response<List<SearchResponse>>;
}