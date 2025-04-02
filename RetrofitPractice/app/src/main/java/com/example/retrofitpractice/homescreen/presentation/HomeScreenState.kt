package com.example.retrofitpractice.homescreen.presentation

import com.example.retrofitpractice.homescreen.data.remote.model.CurrentWeatherResponse
import com.example.retrofitpractice.homescreen.data.remote.model.SearchResponse
import com.example.retrofitpractice.utils.NetworkResponse

data class HomeScreenState(
    val search: String = "",
    val enableClearSearch: Boolean = false,
    val currentWeather: NetworkResponse<CurrentWeatherResponse> = NetworkResponse.Idle,
    val searchResult: List<SearchResponse> = emptyList<SearchResponse>(),
    val loggedOut: Boolean = false
)
