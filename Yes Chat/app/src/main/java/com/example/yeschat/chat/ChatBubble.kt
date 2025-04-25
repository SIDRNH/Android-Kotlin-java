package com.example.yeschat.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yeschat.R
import com.example.yeschat.model.Message
import com.example.yeschat.ui.theme.darkGrey
import com.example.yeschat.ui.theme.purple
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ChatBubble(text: Message) {
    val isCurrentUser: Boolean = text.senderId == Firebase.auth.currentUser?.uid;
    val bubbleColor: Color = if (isCurrentUser) purple else darkGrey;
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        val alignment: Alignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
        Row(
            modifier = Modifier.padding(8.dp)
                .align(alignment),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isCurrentUser) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.friend),
                    contentDescription = "Chat Bubble Icon"
                );
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text.message.trim(),
                color = Color.White,
                modifier = Modifier
                    .background(color = bubbleColor, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            );
        }
    }
}