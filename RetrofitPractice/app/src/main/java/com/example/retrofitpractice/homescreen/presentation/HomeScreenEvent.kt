package com.example.retrofitpractice.homescreen.presentation

sealed interface HomeScreenEvent {
    data class SearchedValue(val search: String): HomeScreenEvent;
    data class Search(val search: String): HomeScreenEvent;
    data object ClearSearch: HomeScreenEvent;
    data object SignOutDialogBox: HomeScreenEvent
    data object SignOut: HomeScreenEvent
}