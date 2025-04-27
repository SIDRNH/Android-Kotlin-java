package com.example.yeschat.chat

import android.net.Uri

sealed interface ChatScreenEvent {
    data class ListenMessages(val chanelId: String): ChatScreenEvent;
    data class TextMessage(val textMessage: String): ChatScreenEvent;
    data class SendMessage(val channelId: String): ChatScreenEvent;
    data object AttachFile: ChatScreenEvent;
    data class CameraPermission(val isGranted: Boolean): ChatScreenEvent;
    data class ImageCaptured(val uri: Uri?): ChatScreenEvent;
}