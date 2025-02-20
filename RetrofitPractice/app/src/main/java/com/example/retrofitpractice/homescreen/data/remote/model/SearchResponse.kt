package com.example.retrofitpractice.homescreen.data.remote.model

data class SearchResponse(
    val country: String,
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val url: String
)