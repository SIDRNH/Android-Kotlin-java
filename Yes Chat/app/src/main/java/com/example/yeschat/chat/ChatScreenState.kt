package com.example.yeschat.chat

import android.net.Uri
import com.example.yeschat.model.Message

data class ChatScreenState(
    val messages: List<Message> = emptyList<Message>(),
    val message: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val isDialogOpen: Boolean = false,
    val cameraImageUri: Uri? = null,
    val requestCameraPermission: Boolean = false,
    val launchCamera: Boolean = false
)
