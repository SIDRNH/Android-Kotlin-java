package com.example.yeschat.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddChannel(state: HomeScreenState, onEvent: (HomeScreenEvent) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Channel");
        Spacer(modifier = Modifier.height(8.dp));
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.channelName,
            onValueChange = { onEvent(HomeScreenEvent.ChannelName(it)) },
            label = { Text(text = "Channel Name") },
            placeholder = { Text(text = "Channel Name") },
            singleLine = true
        );
        Spacer(modifier = Modifier.height(8.dp));
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = state.channelName.isNotEmpty(),
            onClick = {onEvent(HomeScreenEvent.AddChannel)},
            content = {
                Text(text = "Add");
            }
        )
    }
}