package com.example.yeschat.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun ChatScreen(navController: NavController, channelId: String, state: ChatScreenState, onEvent: (ChatScreenEvent) -> Unit) {
    LaunchedEffect(true) {
        onEvent(ChatScreenEvent.ListenMessages(channelId))
    }
    Scaffold(
        containerColor = Color.Black
    ) {
        innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            ChatMessage(channelId = channelId, state = state, onEvent = onEvent);
        }
    }
}