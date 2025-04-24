package com.example.yeschat.chat

sealed interface ChatScreenEvent {
    data class ListenMessages(val chanelId: String): ChatScreenEvent;
    data class TextMessage(val textMessage: String): ChatScreenEvent;
    data class SendMessage(val channelId: String): ChatScreenEvent;
}