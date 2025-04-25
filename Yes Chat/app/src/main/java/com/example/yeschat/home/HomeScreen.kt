package com.example.yeschat.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeschat.ChatScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, state: HomeScreenState, onEvent: (HomeScreenEvent) -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onEvent(HomeScreenEvent.ChannelBottomSheet)},
                content = {
                    Text(
                        text = "Add Channel",
                        modifier = Modifier.padding(15.dp)
                    )
                }
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn {
                item {
                    Text(text = "Messages", color = Color.Gray, fontSize = 20.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(16.dp));
                }
                item {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text(text = "Search") },
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Gray),
                        trailingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        }
                    )
                }
                items(state.channels) { channel ->
                    ChannelItem(
                        channelName = channel.name,
                        onClick = {navController.navigate(ChatScreen(channelId = channel.id))}
                    );
                }
            }
        }
    }
    if (state.channelBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onEvent(HomeScreenEvent.ChannelBottomSheet) },
            sheetState = rememberModalBottomSheetState(),
            content = {
                AddChannel(state = state, onEvent = onEvent);
            }
        )
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(rememberNavController(), state = HomeScreenState(), onEvent = {});
}