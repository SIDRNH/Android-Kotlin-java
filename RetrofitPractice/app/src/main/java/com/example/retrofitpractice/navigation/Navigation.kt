package com.example.retrofitpractice.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.retrofitpractice.authentication.presentation.AuthViewModel
import com.example.retrofitpractice.authentication.presentation.AuthenticationState
import com.example.retrofitpractice.authentication.presentation.UserAuthentication
import com.example.retrofitpractice.di.manual.ManualApplication
import com.example.retrofitpractice.homescreen.presentation.HomeScreen
import com.example.retrofitpractice.homescreen.presentation.HomeScreenState
import com.example.retrofitpractice.homescreen.presentation.HomeScreenViewModel
import com.example.retrofitpractice.ui.theme.RetrofitPracticeTheme
import kotlinx.serialization.Serializable

@Serializable
object UserLogin;

@Serializable
object HomeScreen;

@Composable
fun Navigation() {
    val navController = rememberNavController();

    RetrofitPracticeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {innerPadding ->
            NavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                startDestination = UserLogin
            ) {
                composable<UserLogin> {
                    val authViewModel: AuthViewModel = viewModel<AuthViewModel>(
                        factory = ManualApplication.appModule.authViewModelFactory
                    );
                    val state: AuthenticationState = authViewModel.state.collectAsState().value;

                    UserAuthentication(state = state, onEvent = authViewModel::onEvent)
                }

                composable<HomeScreen> {
                    val homeScreenViewModel: HomeScreenViewModel = viewModel<HomeScreenViewModel>(
                        factory = ManualApplication.appModule.homeScreenViewModelFactory
                    );
                    val state: HomeScreenState = homeScreenViewModel.state.collectAsState().value;
                    HomeScreen(state = state, onEvent = homeScreenViewModel::onEvent);
                }
            }
        }
    }
}