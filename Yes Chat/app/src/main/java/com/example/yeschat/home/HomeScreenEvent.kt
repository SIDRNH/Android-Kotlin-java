package com.example.yeschat.home

sealed interface HomeScreenEvent {
    data object LoadChannels: HomeScreenEvent;
    data object ChannelBottomSheet: HomeScreenEvent;
    data object AddChannel: HomeScreenEvent;
    data class ChannelName(val name: String): HomeScreenEvent;
}