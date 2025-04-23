package com.example.yeschat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yeschat.auth.login.LoginScreen
import com.example.yeschat.auth.login.LoginScreenState
import com.example.yeschat.auth.login.LoginScreenViewModel
import com.example.yeschat.auth.signup.SignUpScreen
import com.example.yeschat.auth.signup.SignUpScreenState
import com.example.yeschat.auth.signup.SignUpScreenViewModel
import com.example.yeschat.di.Application
import com.example.yeschat.home.HomeScreen
import com.example.yeschat.splashscreen.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
object Splash;

@Serializable
object Login;

@Serializable
object SignUp;

@Serializable
object HomeScreen;

@Composable
fun Navigation() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val navController: NavHostController = rememberNavController();

        NavHost(navController = navController, startDestination = Splash){
            composable<Splash> {
                SplashScreen(navController = navController);
            }

            composable<Login> {
                val loginScreenViewModel: LoginScreenViewModel = viewModel<LoginScreenViewModel>(
                    factory = Application.appModule.loginScreenViewModelFactory
                );
                val state: LoginScreenState by loginScreenViewModel.state.collectAsState()
                LoginScreen(navController = navController, state = state, onEvent = loginScreenViewModel::onEvent);
            }

            composable<SignUp> {
                val signUpScreenViewModel: SignUpScreenViewModel = viewModel<SignUpScreenViewModel>(
                    factory = Application.appModule.signUpScreenViewModelFactory
                );
                val state: SignUpScreenState by signUpScreenViewModel.state.collectAsState()
                SignUpScreen(navController = navController, state = state, onEvent = signUpScreenViewModel::onEvent);
            }

            composable<HomeScreen> {
                HomeScreen(navController = navController);
            }
        }
    }
}