package com.example.yeschat.auth.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeschat.HomeScreen
import com.example.yeschat.R
import com.example.yeschat.SignUp

@Composable
fun LoginScreen(navController: NavController, state: LoginScreenState, onEvent: (LoginScreenEvent) -> Unit) {
    val focusManager: FocusManager = LocalFocusManager.current;
    val focusRequester: FocusRequester = remember{ FocusRequester() };
    val context: Context = LocalContext.current;

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            Toast.makeText(context, "Log in Successful", Toast.LENGTH_SHORT).show()
            navController.navigate(HomeScreen) {
                popUpTo(0)
            }
        }
    }
    LaunchedEffect(state.error) {
        if (!state.error.isNullOrEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo"
            );
            Spacer(modifier = Modifier.height(16.dp));
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = state.email,
                onValueChange = {onEvent(LoginScreenEvent.Email(it))},
                placeholder = {Text(text = "Email")},
                label = {Text(text = "Email")},
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequester.requestFocus();
                    }
                )
            );
            Spacer(modifier = Modifier.height(8.dp));
            OutlinedTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = state.password,
                onValueChange = {onEvent(LoginScreenEvent.Password(it))},
                placeholder = {Text(text = "Password")},
                label = {Text(text = "Password")},
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus();
                        onEvent(LoginScreenEvent.Login);
                    }
                )
            );
            Spacer(modifier = Modifier.height(16.dp));
            if (state.loading) {
                CircularProgressIndicator();
            }else {
                Button(
                    onClick = {onEvent(LoginScreenEvent.Login)},
                    shape = RoundedCornerShape(3.dp),
                    content = {
                        Text(text = "Log in")
                    },
                    enabled = state.email.isNotEmpty() && state.password.isNotEmpty()
                );
                Spacer(modifier = Modifier.height(16.dp));
                TextButton(
                    onClick = {
                        navController.navigate(SignUp) {
                            launchSingleTop = true
                        }
                    },
                    content = {
                        Text(text = "Create an account?")
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(rememberNavController(), state = LoginScreenState(""), onEvent = {});
}