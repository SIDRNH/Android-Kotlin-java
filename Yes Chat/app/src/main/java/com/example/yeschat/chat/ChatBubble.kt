package com.example.yeschat.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yeschat.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ChatBubble(text: Message) {
    val isCurrentUser: Boolean = text.senderId == Firebase.auth.currentUser?.uid
    val bubbleColor: Color = if (isCurrentUser) Color.Blue else Color.Green
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        val alignment: Alignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
        Box(
            modifier = Modifier.padding(8.dp)
                .background(color = bubbleColor, shape = RoundedCornerShape(8.dp))
                .align(alignment),
        ) {
            Text(
                text = text.message,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}