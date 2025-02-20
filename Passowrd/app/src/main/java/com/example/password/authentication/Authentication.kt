package com.example.password.authentication

import android.util.Log
import com.example.password.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.password.components.SocialMediaAuth
import com.example.password.components.TextFields
import com.example.password.navigation.DashboardScreen
import com.example.password.navigation.UserLoginScreen
import com.example.password.navigation.UserSignupScreen
import com.example.password.ui.theme.Black
import com.example.password.ui.theme.BlueGray
import com.example.password.ui.theme.PasswordTheme
import com.example.password.ui.theme.Roboto
import com.example.password.ui.theme.White

@Composable
fun UserLoginAuthentication(navController: NavController): Unit {
    PasswordTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (isSystemInDarkTheme()) Black else White)
            ) {
                TopSection();
                Spacer(modifier = Modifier.height(36.dp));
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)
                ) {
                    LoginSection(navController = navController);
                    Spacer(modifier = Modifier.height(30.dp));
                    SocialMediaSection();
                    BottomSection(onButtonClick = {navController.navigate(UserSignupScreen)});
                }
            }
        }
    }
}

@Composable
fun UserSignupAuthentication(navController: NavController): Unit {
    PasswordTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (isSystemInDarkTheme()) Black else White)
            ) {
                TopSection(login = false);
                Spacer(modifier = Modifier.height(36.dp));
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)
                ) {
                    LoginSection(navController = navController, login = false);
                    Spacer(modifier = Modifier.height(30.dp));
                    SocialMediaSection();
                    BottomSection(login = false, onButtonClick = {
                        navController.navigate(UserLoginScreen)
                    });
                }
            }
        }
    }
}

@Composable
private fun TopSection(login: Boolean = true): Unit {
    val uiColor: Color = if (isSystemInDarkTheme()) White else Black;
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.46f),
            painter = painterResource(R.drawable.shape),
            contentScale = ContentScale.FillBounds,
            contentDescription = stringResource(id = R.string.home_screen_background_image)
        );
        Row(
            modifier = Modifier.padding(top = 80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.title_name),
                style = MaterialTheme.typography.headlineLarge,
                color = uiColor
            );
        }
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(Alignment.BottomCenter),
            text = stringResource(id = if (login) R.string.auth_login else R.string.auth_signup),
            style = MaterialTheme.typography.headlineMedium,
            color = uiColor
        )
    }
}

@Composable
private fun LoginSection(navController: NavController, login: Boolean = true): Unit {
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel<AuthenticationViewModel>();
    val authState by authenticationViewModel.authState.observeAsState();
    TextFields(
        label = "Email",
        trailing = "",
        value = authenticationViewModel.email,
        onValueChange = authenticationViewModel::onEmailChange,
        modifier = Modifier.fillMaxWidth()
    );
    Spacer(modifier = Modifier.height(15.dp));
    TextFields(
        label = "Password",
        trailing = "Show",
        password = true,
        value = authenticationViewModel.password,
        onValueChange = authenticationViewModel::onPasswordChange,
        modifier = Modifier.fillMaxWidth()
    );
    Spacer(modifier = Modifier.height(20.dp));
    Button(
        enabled = authenticationViewModel.isFormValid(),
        onClick = {
            if (login) {
                authenticationViewModel.login();
            }
            else {
                authenticationViewModel.signup();
                navController.navigate(UserLoginScreen);
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) BlueGray else Black,
            contentColor = White,
            disabledContainerColor = if (isSystemInDarkTheme()) Color(0xFF1E293B) else Color(0xFFBFDBFE)
        ),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = if (login) "Log In" else "Sign Up",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
        )
    }

    LaunchedEffect(authState) {
        when(authState) {
            is AuthState.Loading -> "";
            is AuthState.Authenticated -> navController.navigate(DashboardScreen);
            is AuthState.Error -> {
                val errorMessage = (authState as AuthState.Error).message;
                Log.d("Failed Authentication", errorMessage);
            }
            else -> Log.d("Unauthenticated", "Unauthenticated");
        }
    }
}

@Composable
private fun SocialMediaSection(): Unit {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Or Continue with",
            style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF64748B))
        );
        Spacer(modifier = Modifier.height(20.dp));
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialMediaAuth(icon = R.drawable.google_image) { }
            Spacer(modifier = Modifier.width(20.dp))
            SocialMediaAuth(icon = R.drawable.facebook_image) { }
            Spacer(modifier = Modifier.width(20.dp))
            SocialMediaAuth(icon = R.drawable.apple_image) { }
            Spacer(modifier = Modifier.width(20.dp))
            SocialMediaAuth(icon = R.drawable.microsoft_image) { }
        }
    }
}

@Composable
private fun BottomSection(login: Boolean = true, onButtonClick: (() -> Unit)? = null): Unit {
    val uiColor: Color = if (isSystemInDarkTheme()) White else Black;
    Box(
        modifier = Modifier
            .fillMaxHeight(fraction = 0.8f)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row {
            Text(
                text = stringResource(id = if (login) R.string.dont_have_an_account else R.string.already_have_an_account),
                color = Color(0xFF94A3B8),
                fontSize = 14.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal
            );
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = {
                        onButtonClick?.invoke();
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),

                text = stringResource(id = if (login) R.string.create_account else R.string.auth_login),
                color = uiColor,
                fontSize = 14.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium
            )
        }
    }
}