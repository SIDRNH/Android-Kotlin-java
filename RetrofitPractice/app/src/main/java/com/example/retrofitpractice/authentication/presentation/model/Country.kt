package com.example.retrofitpractice.authentication.presentation.model

data class Country(
    val name: String,
    val emoji: String,
    val isoCode: String,
    val code: String,
    val limit: Int
)

val countries: List<Country> = listOf(
    Country("India", "\uD83C\uDDEE\uD83C\uDDF3", "IN", "+91", 10),
    Country("United States","\uD83C\uDDFA\uD83C\uDDF8", "US", "+1", 10),
    Country("United Kingdom","\uD83C\uDDEC\uD83C\uDDE7", "GB", "+44", 10),
    Country("Germany","\uD83C\uDDE9\uD83C\uDDEA", "DE", "+4945", 13),
    Country("Canada","\uD83C\uDDE8\uD83C\uDDE6", "CA", "+1", 10)
)