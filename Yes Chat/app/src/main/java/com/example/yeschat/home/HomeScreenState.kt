package com.example.yeschat.home

import com.example.yeschat.model.Channel

data class HomeScreenState(
    val channels: List<Channel> = emptyList<Channel>(),
    val loading: Boolean = false,
    val error: String? = null,
    val channelBottomSheet: Boolean = false,
    val channelName: String = "",
)
