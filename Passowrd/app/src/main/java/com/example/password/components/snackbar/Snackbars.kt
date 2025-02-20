package com.example.password.components.snackbar

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null
)

data class SnackbarAction(
    val name: String,
    val action: () -> Unit
)

object Snackbars {
    private val _events = Channel<SnackbarEvent>();
    val events = _events.receiveAsFlow();
    suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event);
    }
}