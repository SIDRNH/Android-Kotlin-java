package com.example.retrofitpractice.homescreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.retrofitpractice.R
import com.example.retrofitpractice.homescreen.data.remote.model.CurrentWeatherResponse
import com.example.retrofitpractice.navigation.UserLogin
import com.example.retrofitpractice.utils.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(state: HomeScreenState, onEvent: (HomeScreenEvent) -> Unit, navController: NavController) {
    val focusRequester: FocusRequester = remember { FocusRequester() };
    val focusManager: FocusManager = LocalFocusManager.current;

    //Navigation Drawer
    val drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed);
    val scope: CoroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                ProfileCard(name = state.userName, phoneNumber = state.userPhoneNumber, photoUrl = state.userPicture);
                NavigationDrawerItem(
                    label = { Text("Sign Out") },
                    onClick = { onEvent(HomeScreenEvent.SignOutDialogBox) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Sign OUt"
                        )
                    }
                )
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Home") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open();
                                }
                            },
                            content = {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu");
                            }
                        )
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        top = innerPadding.calculateTopPadding() + 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = state.search,
                    onValueChange = {onEvent(HomeScreenEvent.SearchedValue(it))},
                    placeholder = { Text(stringResource(R.string.search)) },
                    trailingIcon = {
                        if(state.enableClearSearch) {
                            IconButton(onClick = {onEvent(HomeScreenEvent.ClearSearch)}) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = stringResource(R.string.clear_search));
                            };
                        }else {
                            IconButton(
                                onClick = {
                                    focusManager.clearFocus();
                                    onEvent(HomeScreenEvent.Search(state.search));
                                },
                                enabled = state.search.trim().isNotBlank()
                            ) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search));
                            };
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (state.search.trim().isNotBlank()) {
                                focusManager.clearFocus()
                                onEvent(HomeScreenEvent.Search(state.search))
                            }
                        }
                    )
                );
                if (state.searchResult.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                            .heightIn(max = 250.dp)
                    ) {
                        items(state.searchResult) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        onClick = {
                                            focusManager.clearFocus();
                                            onEvent(HomeScreenEvent.Search(it.name));
                                        }
                                    ),
                                text = "${it.name}, ${it.country}",
                                color = Color.Black
                            )
                        }
                    }
                }
                when(val result = state.currentWeather) {
                    is NetworkResponse.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                            content = { Text(text = result.message); }
                        );
                    }
                    NetworkResponse.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                            content = { CircularProgressIndicator(); }
                        );
                    }
                    is NetworkResponse.Success -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            content = { WeatherDetails(data = result.data); }
                        );
                    }
                    NetworkResponse.Idle -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                            content = { Text(text = "No Data"); }
                        );
                    }
                }
            }
        }
    }
    if (state.signOutDialogBox) {
        AlertDialog(
            onDismissRequest = { onEvent(HomeScreenEvent.SignOutDialogBox) },
            title = { Text(text = "Sign Out") },
            text = { Text(text = "Do you want to Sign Out") },
            confirmButton = {
                Button(
                    onClick = {
                        onEvent(HomeScreenEvent.SignOutDialogBox);
                        onEvent(HomeScreenEvent.SignOut);
                    },
                    content = {Text("OK")}
                )
            },
            dismissButton = {
                Button(
                    onClick = { onEvent(HomeScreenEvent.SignOutDialogBox) },
                    content = {Text("Cancel")},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    )
                )
            }
        )
    }

    LaunchedEffect(state.loggedOut) {
        if (state.loggedOut) {
            navController.navigate(UserLogin) {
                popUpTo(0)
            }
        }
    }
}

@Composable
fun WeatherDetails(data : CurrentWeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location icon",
                modifier = Modifier.size(40.dp)
            )
            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = " ${data.current.temp_c} ° c",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${data.current.condition.icon}".replace("64x64","128x128"),
            contentDescription = "Condition icon"
        )
        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Humidity",data.current.humidity.toString())
                    WeatherKeyVal("Wind Speed",data.current.wind_kph.toString()+" km/h")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("UV",data.current.uv.toString())
                    WeatherKeyVal("Participation",data.current.precip_mm.toString()+" mm")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Local Time",data.location.localtime.split(" ")[1])
                    WeatherKeyVal("Local Date",data.location.localtime.split(" ")[0])
                }
            }
        }
    }
}

@Composable
fun WeatherKeyVal(key : String, value : String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}

@Composable
fun ProfileCard(name: String?, phoneNumber: String?, photoUrl: String?) {
    Card(
        modifier = Modifier.fillMaxWidth().height(88.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.3f),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(4.dp),
                    model = photoUrl,
                    placeholder = painterResource(R.drawable.profile_default),
                    error = painterResource(R.drawable.profile_default),
                    contentScale = ContentScale.Crop,
                    contentDescription = "ProfilePicture"
                )
            }
            Spacer(modifier = Modifier.width(4.dp));
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.7f),
                contentAlignment = Alignment.CenterStart
            ) {
                Column(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("$name");
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("$phoneNumber")
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//fun PreviewProfileCard() {
//
//}