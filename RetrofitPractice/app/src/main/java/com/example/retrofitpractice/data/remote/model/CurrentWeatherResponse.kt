package com.example.retrofitpractice.data.remote.model

data class CurrentWeatherResponse(
    val current: Current,
    val location: Location
)