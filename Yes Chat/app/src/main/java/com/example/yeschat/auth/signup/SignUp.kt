package com.example.yeschat.auth.signup

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yeschat.Login
import com.example.yeschat.R

@Composable
fun SignUpScreen(navController: NavController, state: SignUpScreenState, onEvent: (SignUpScreenEvent) -> Unit) {
    val context: Context = LocalContext.current;
    val focusManager: FocusManager = LocalFocusManager.current;
    val nameFocusRequester: FocusRequester = remember{ FocusRequester() };
    val emailFocusRequester: FocusRequester = remember{ FocusRequester() };
    val passwordFocusRequester: FocusRequester = remember{ FocusRequester() };

    LaunchedEffect(state.signUpSuccess) {
        if (state.signUpSuccess) {
            Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_SHORT).show()
            navController.navigate(Login) {
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
            modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color.White),
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
                modifier = Modifier.focusRequester(nameFocusRequester),
                value = state.name,
                onValueChange = {onEvent(SignUpScreenEvent.Name(it))},
                placeholder = {Text(text = "Name")},
                label = {Text(text = "Name")},
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        emailFocusRequester.requestFocus();
                    }
                )
            );
            Spacer(modifier = Modifier.height(16.dp));
            OutlinedTextField(
                modifier = Modifier.focusRequester(emailFocusRequester),
                value = state.email,
                onValueChange = {onEvent(SignUpScreenEvent.Email(it))},
                placeholder = {Text(text = "Email")},
                label = {Text(text = "Email")},
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus();
                    }
                ),
                isError = !state.validEmail && state.emailTouched
            );
            Spacer(modifier = Modifier.height(8.dp));
            OutlinedTextField(
                modifier = Modifier.focusRequester(passwordFocusRequester),
                value = state.password,
                onValueChange = {onEvent(SignUpScreenEvent.Password(it))},
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
                        onEvent(SignUpScreenEvent.SignUp);
                    }
                ),
                isError = !state.validPassword && state.passwordTouched
            );
            Spacer(modifier = Modifier.height(16.dp));
            if (state.loading) {
                CircularProgressIndicator()
            }else {
                Button(
                    onClick = {onEvent(SignUpScreenEvent.SignUp)},
                    shape = RoundedCornerShape(3.dp),
                    content = {
                        Text(text = "Sign Up")
                    },
                    enabled = state.name.isNotEmpty() && state.validEmail && state.validPassword
                );
                Spacer(modifier = Modifier.height(16.dp));
                TextButton(
                    onClick = {
                        navController.navigate(Login) {
                            popUpTo(Login) {
                                inclusive = true;
                            };
                            launchSingleTop = true;
                        };
                    },
                    content = {
                        Text(text = "Already have an account?")
                    }
                )
            }
        }
    }
}