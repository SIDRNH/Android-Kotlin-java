package com.example.retrofitpractice.authentication.presentation

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofitpractice.authentication.presentation.model.Country

@Composable
fun UserAuthentication(state: AuthenticationState, onEvent: (AuthenticationEvent) -> Unit) {
    val focusRequester: FocusRequester = remember { FocusRequester() };
    val focusManager: FocusManager = LocalFocusManager.current;
    val context = LocalContext.current;
    val activity = context as? Activity;

    Scaffold {innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .border(width = 2.dp, color = Color.Red)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Login", fontSize = 52.sp);



                OutlinedTextField(
                    modifier = Modifier.focusRequester(focusRequester = focusRequester),
                    value = state.phoneNumber,
                    onValueChange = {onEvent(AuthenticationEvent.PhoneNumber(it))},
                    singleLine = true,
                    placeholder = { Text("Phone Number")},
                    prefix = {
                        Row(
                            modifier = Modifier.clickable { onEvent(AuthenticationEvent.CCDClicked) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "${Country.INDIA.countryCode} ${Country.INDIA.countryIso}");
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Country Selector")
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (state.phoneNumber.trim().isNotBlank()) {
                                focusManager.clearFocus();
                                onEvent(AuthenticationEvent.SendOtp(activity!!, state.phoneNumber));
                            }
                        }
                    )
                )



                Button(
                    enabled = state.phoneNumber.trim().isNotBlank(),
                    onClick = {
                        focusManager.clearFocus();
                        onEvent(AuthenticationEvent.SendOtp(activity!!, state.phoneNumber));
                    },
                    content = { Text("Send Otp")}
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAuthentication() {
    UserAuthentication(state = AuthenticationState(), onEvent = {})
}