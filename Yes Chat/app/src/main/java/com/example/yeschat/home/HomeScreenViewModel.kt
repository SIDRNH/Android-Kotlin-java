package com.example.yeschat.home

import androidx.lifecycle.ViewModel
import com.example.yeschat.model.Channel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeScreenViewModel(private val firebaseDatabase: FirebaseDatabase): ViewModel() {
    private val _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState());
    val state: StateFlow<HomeScreenState> = _state.asStateFlow();

    init {
        onEvent(HomeScreenEvent.LoadChannels);
    }

    fun onEvent(event: HomeScreenEvent) {
        when(event) {
            HomeScreenEvent.LoadChannels -> getChannels();
            HomeScreenEvent.ChannelBottomSheet -> {
                _state.update {
                    it.copy(
                        channelBottomSheet = !_state.value.channelBottomSheet
                    )
                }
            }
            HomeScreenEvent.AddChannel -> {
                addChannel(_state.value.channelName);
                onEvent(HomeScreenEvent.ChannelBottomSheet);
            };
            is HomeScreenEvent.ChannelName -> {
                _state.update {
                    it.copy(
                        channelName = event.name
                    )
                }
            }
        }
    }

    private fun getChannels() {
        _state.update { it.copy(loading = true, error = null) };
        //fetching Channels from Database
        firebaseDatabase.getReference("channel").get()
            .addOnSuccessListener { dataSnapshot ->
                val list = dataSnapshot.children.mapNotNull { child ->
                    child.key?.let { Channel(it, child.value.toString()) }
                }
                _state.update { it.copy(channels = list, loading = false) }
            }.addOnFailureListener { e ->
                _state.update { it.copy(loading = false, error = e.localizedMessage ?: "Something Went Wrong") }
            }
    }

    private fun addChannel(channelName: String) {
        _state.update { it.copy(loading = true, error = null) };
        //Adding Channel
        val channelRef = firebaseDatabase.getReference("channel").push();
        val key = channelRef.key;
        if (key != null) {
            channelRef.setValue(channelName)
                .addOnSuccessListener {
                    onEvent(HomeScreenEvent.LoadChannels);
                    _state.update { it.copy(loading = false) };
                }.addOnFailureListener { e ->
                    _state.update { it.copy(loading = false, error = e.localizedMessage ?: "Failed to Add Channel") };
                }
        }else {
            _state.update { it.copy(loading = false, error = "Failed to Add Channel") };
        }
    }
}