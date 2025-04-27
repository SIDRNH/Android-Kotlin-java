package com.example.yeschat.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yeschat.R
import com.example.yeschat.ui.theme.darkGrey

@Composable
fun ChatMessage(channelId: String, state: ChatScreenState, onEvent: (ChatScreenEvent) -> Unit) {
    val hideKeyboard: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current;
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(state.messages) { item ->
                ChatBubble(text = item);
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(color = darkGrey)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(
                onClick = {onEvent(ChatScreenEvent.AttachFile)},
                content = {
                    Image(painter = painterResource(R.drawable.attach), contentDescription = "Attach Image or File")
                }
            );
            TextField(
                value = state.message,
                onValueChange = {onEvent(ChatScreenEvent.TextMessage(it))},
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Type a Message", color = Color.White) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { hideKeyboard?.hide() }
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = darkGrey,
                    focusedContainerColor = darkGrey,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            );
            IconButton(
                onClick = {onEvent(ChatScreenEvent.SendMessage(channelId = channelId))},
                content = {
                    Image(painter = painterResource(R.drawable.send), contentDescription = "Send Message")
                }
            );
        }
    }
}

@Preview
@Composable
fun PreviewChatMessage() {
    ChatMessage("", ChatScreenState()) {};
}