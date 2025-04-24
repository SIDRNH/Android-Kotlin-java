package com.example.yeschat.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController

@Composable
fun ChatScreen(navController: NavController, channelId: String, state: ChatScreenState, onEvent: (ChatScreenEvent) -> Unit) {
    LaunchedEffect(true) {
        onEvent(ChatScreenEvent.ListenMessages(channelId))
    }


}