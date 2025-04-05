package com.example.retrofitpractice.homescreen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitpractice.homescreen.data.remote.model.CurrentWeatherResponse
import com.example.retrofitpractice.homescreen.data.remote.model.SearchResponse
import com.example.retrofitpractice.homescreen.domain.WeatherRepository
import com.example.retrofitpractice.roomdb.UserRepository
import com.example.retrofitpractice.utils.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class HomeScreenViewModel(
    private val weatherRepository: WeatherRepository,
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository
): ViewModel() {

    init {
        fetchUser();
    }
    private val _state = MutableStateFlow(HomeScreenState());
    val state: StateFlow<HomeScreenState> = _state.asStateFlow();

    fun onEvent(event: HomeScreenEvent) {
        when(event) {
            is HomeScreenEvent.SearchedValue -> {
                _state.update {
                    it.copy(
                        search = event.search,
                        searchResult = emptyList()
                    );
                }
                searchQuery(location = event.search);
            }
            is HomeScreenEvent.Search -> {
                _state.update {
                    it.copy(
                        search = event.search,
                        enableClearSearch = true,
                        searchResult = emptyList()
                    );
                }
                getData(location = event.search);
            }
            HomeScreenEvent.ClearSearch -> {
                _state.update {
                    it.copy(
                        search = "",
                        enableClearSearch = false,
                        currentWeather = NetworkResponse.Idle,
                        searchResult = emptyList()
                    );
                }
            }
            HomeScreenEvent.SignOutDialogBox -> {
                _state.update {
                    it.copy(
                        signOutDialogBox = !_state.value.signOutDialogBox
                    )
                }
            }
            HomeScreenEvent.SignOut -> {
                auth.signOut();
                _state.update {
                    it.copy(
                        loggedOut = true
                    )
                }
                deleteAllUsers();
            }
        }
    }

    private fun getData(location: String) {
        _state.update {
            it.copy(
                currentWeather = NetworkResponse.Loading
            );
        }
        viewModelScope.launch{
            try {
                val result: Response<CurrentWeatherResponse> = weatherRepository.getCurrentWeather(location = location);
                if (result.isSuccessful) {
                    result.body()?.let {
                        _state.update {
                            it.copy(
                               currentWeather = result.body()?.let {body ->
                                   NetworkResponse.Success(body)
                               } ?: NetworkResponse.Error("Empty Response")
                            );
                        }
                    }
                }else {
                    val errorBody = result.errorBody()?.string() ?: result.message();
                    val errorMessage = try {
                        JSONObject(errorBody ?: "").getJSONObject("error").getString("message")
                    } catch (e: Exception) {
                        Log.e("Exception", "$e")
                        "Something went wrong"
                    }
                    _state.update {
                        it.copy(
                            currentWeather = NetworkResponse.Error(errorMessage)
                        );
                    }
                }
            } catch(e: Exception) {
                _state.update {
                    it.copy(
                        currentWeather = NetworkResponse.Error("Something went wrong")
                    );
                }
                Log.e("HomeScreenViewModel", "Exception occurred in API call: ${e.localizedMessage}", e);
            }
        }
    }

    private fun searchQuery(location: String) {
        if (location.length < 3) {
            _state.update {
                it.copy(
                    searchResult = emptyList()
                );
            }
            return;
        }
        viewModelScope.launch {
            try {
                val result: Response<List<SearchResponse>> = weatherRepository.searchQuery(location = location);
                if (result.isSuccessful) {
                    result.body()?.let {data ->
                        _state.update {
                            it.copy(
                                searchResult = data
                            );
                        }
                    }
                } else {
                    val errorBody = result.errorBody()?.string() ?: result.message();
                    try {
                        JSONObject(errorBody ?: "").getJSONObject("error").getString("message")
                    } catch (e: Exception) {
                        Log.e("Exception", "$e")
                        "Something went wrong"
                    }
                    _state.update {
                        it.copy(
                            searchResult = emptyList()
                        );
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        searchResult = emptyList()
                    );
                }
                Log.e("HomeScreenViewModel", "Exception occurred in Search API call: ${e.localizedMessage}", e);
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            userRepository.fetchUser().collect { user ->
                if (user != null) {
                    _state.update {
                        it.copy(
                            userName = user.name ?: "Guest",
                            userPhoneNumber = user.phoneNumber ?: "Not Available",
                            userPicture = user.photoUrl ?: ""
                        )
                    }
                }else {
                    _state.update {
                        it.copy(
                            userName = "Guest",
                            userPhoneNumber = "Not Available",
                            userPicture = ""
                        )
                    }
                }
            }
        }
    }

    private fun deleteAllUsers() {
        viewModelScope.launch {
            userRepository.deleteAllUsers();
        }
    }
}