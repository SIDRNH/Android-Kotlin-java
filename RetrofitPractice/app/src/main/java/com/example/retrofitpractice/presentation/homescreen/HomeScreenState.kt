package com.example.retrofitpractice.presentation.homescreen

import com.example.retrofitpractice.data.remote.model.CurrentWeatherResponse
import com.example.retrofitpractice.data.remote.model.SearchResponse
import com.example.retrofitpractice.utils.NetworkResponse

data class HomeScreenState(
    val search: String = "",
    val enableClearSearch: Boolean = false,
    val currentWeather: NetworkResponse<CurrentWeatherResponse> = NetworkResponse.Idle,
    val searchResult: List<SearchResponse> = emptyList<SearchResponse>()
)
