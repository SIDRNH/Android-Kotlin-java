package com.example.retrofitpractice.authentication.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.retrofitpractice.authentication.presentation.model.countries
import com.example.retrofitpractice.navigation.HomeScreen

@Composable
fun UserAuthentication(state: AuthenticationState, onEvent: (AuthenticationEvent) -> Unit, navController: NavController) {
    val context = LocalContext.current;
    val activity = context as ComponentActivity;

    Scaffold {innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Login", fontSize = 52.sp);

                if (!state.sentOtp) {
                    OutlinedTextField(
                        value = state.phoneNumber,
                        onValueChange = {
                            if (it.length <= state.limit && it.all { it.isDigit() }) {
                                onEvent(AuthenticationEvent.PhoneNumber(it))
                            }
                        },
                        placeholder = { Text("Enter Your Phone Number") },
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Phone
                        ),
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        onClick = { onEvent(AuthenticationEvent.CCDExpanded) },
                                        indication = null
                                    )
                            ) {
                                Row {
                                    Text(state.selectedOption.code)
                                    Icon(
                                        imageVector = if (state.isCCDExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = "DropDown"
                                    )
                                }
                            }
                            DropdownMenu(
                                expanded = state.isCCDExpanded,
                                onDismissRequest = { onEvent(AuthenticationEvent.CCDExpanded) }
                            ) {
                                countries.forEach {
                                    DropdownMenuItem(
                                        text = {
                                            Row {
                                                Text(text = it.emoji)
                                                Spacer(modifier = Modifier.width(2.dp))
                                                Text(text = it.isoCode)
                                                Spacer(modifier = Modifier.width(2.dp))
                                                Text(text = it.code)
                                                Spacer(modifier = Modifier.width(2.dp))
                                                Text(text = it.name)
                                            }
                                        },
                                        onClick = {
                                            if (state.selectedOption.code != it.code) {
                                                onEvent(AuthenticationEvent.DropdownSelectedItem(it));
                                            }
                                            onEvent(AuthenticationEvent.CCDExpanded);
                                        }
                                    )
                                }
                            }
                        }
                    )
                }else {
                    OutlinedTextField(
                        value = state.otp,
                        onValueChange = { onEvent(AuthenticationEvent.Otp(it)) },
                        label = { Text("OTP") },
                        placeholder = { Text("Enter Your OTP") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }

                Button(
                    enabled = if (!state.sentOtp) state.phoneNumber.trim().length == state.limit else state.otp.length == 6,
                    onClick = {
                        if (!state.sentOtp) onEvent(AuthenticationEvent.SendOtp(activity = activity, phoneNumber = state.phoneNumber));
                        else onEvent(AuthenticationEvent.VerifyOtp)
                    },
                    content = { if (!state.sentOtp) Text("Send Otp") else Text("verify Otp") }
                )
            }
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.size(width = 180.dp, height = 60.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator();
                            Spacer(modifier = Modifier.width(8.dp));
                            Text("Loading...")
                        }
                    }
                }
            }
        }
        if (state.loggedIn) {
            navController.navigate(HomeScreen)
        }
    }
}