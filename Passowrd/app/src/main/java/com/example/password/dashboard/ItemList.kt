package com.example.password.dashboard

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.password.authentication.AuthenticationViewModel
import com.example.password.components.AddItem
import com.example.password.components.DropDownContextMenu
import com.example.password.components.DropDownItem
import com.example.password.components.Item
import com.example.password.navigation.UserLoginScreen
import com.example.password.objects.item.Item
import com.example.password.objects.item.ItemEvent
import com.example.password.objects.item.ItemState
import com.example.password.objects.item.ItemViewModel
import com.example.password.ui.theme.PaleCornFlowerBlue
import com.example.password.ui.theme.PasswordTheme
import com.example.password.ui.theme.SquidInk

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ItemList(navController: NavController, categoryId: Int, categoryName: String, state: ItemState, onEvent: (ItemEvent) -> Unit, viewModel: ItemViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior();
    val topAppbarColor = if (isSystemInDarkTheme()) SquidInk else PaleCornFlowerBlue;
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel<AuthenticationViewModel>();

    LaunchedEffect(categoryId) {
        viewModel.setCategoryId(categoryId);
    }
    PasswordTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(categoryName);
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = topAppbarColor
                    ),
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            authenticationViewModel.signOut();
                            navController.navigate(UserLoginScreen)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = "Sign Out"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onEvent(ItemEvent.ShowDialog);
                    },
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Account"
                    );
                }
            }
        ) {innerPadding ->
            if (state.items.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Accounts available");
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.items) { item ->
                        Item(
                            onClick = {onEvent(ItemEvent.EditItem(item = item))},
                            itemName = item.name,
                            dropDownItems = listOf<DropDownItem>(DropDownItem(text = "Delete")),
                            onItemClick = {onEvent(ItemEvent.DeleteItem(item))},
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            if (state.isAddingItem) {
                AddItem(state = state, onEvent = onEvent);
            }
        }
    }
}