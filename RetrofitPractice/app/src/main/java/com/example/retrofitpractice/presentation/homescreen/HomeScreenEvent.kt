package com.example.retrofitpractice.presentation.homescreen

sealed interface HomeScreenEvent {
    data class SearchedValue(val search: String): HomeScreenEvent;
    data class Search(val search: String): HomeScreenEvent;
    object ClearSearch: HomeScreenEvent;
}