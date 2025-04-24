package com.example.yeschat.chat

import com.example.yeschat.model.Message

data class ChatScreenState(
    val messages: List<Message> = emptyList<Message>(),
    val message: String = "",
    val loading: Boolean = false,
    val error: String? = null,
)
