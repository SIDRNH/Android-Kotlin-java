package com.example.password.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.password.authentication.AuthState
import com.example.password.authentication.AuthenticationViewModel
import com.example.password.authentication.UserLoginAuthentication
import com.example.password.authentication.UserSignupAuthentication
import com.example.password.dashboard.Dashboard
import com.example.password.dashboard.ItemList
import com.example.password.objects.category.CategoryViewModel
import com.example.password.objects.item.ItemViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


@Serializable
object UserLoginScreen;

@Serializable
object UserSignupScreen;

@Serializable
object DashboardScreen;

@Serializable
data class ItemScreen(val categoryId: Int, val categoryName: String);

@Composable
fun Navigation() {
    val navController = rememberNavController();
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel<AuthenticationViewModel>();
    val authState by authenticationViewModel.authState.observeAsState(initial = AuthState.Loading);
    val categoryViewModel: CategoryViewModel = hiltViewModel<CategoryViewModel>();
    val itemViewModel: ItemViewModel = hiltViewModel<ItemViewModel>();

    when (authState) {
        is AuthState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is AuthState.Authenticated -> {
            LaunchedEffect(Unit) {
                navController.navigate(DashboardScreen) {
                    popUpTo(UserLoginScreen) { inclusive = true } // Clears login from back stack
                }
            }
        }
        is AuthState.Unauthenticated -> {
            LaunchedEffect(Unit) {
                navController.navigate(UserLoginScreen) {
                    popUpTo(0) { inclusive = true } // Clears everything and prevents flickering
                }
            }
        }

        is AuthState.Error -> {
            val errorMessage = (authState as AuthState.Error).errorMessage
            // Show Snackbar for errors
            LaunchedEffect(errorMessage) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = if (authState is AuthState.Authenticated) DashboardScreen else UserLoginScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<UserLoginScreen> {
                UserLoginAuthentication(navController = navController);
            }
            composable<UserSignupScreen> {
                UserSignupAuthentication(navController = navController);
            }
            composable<DashboardScreen> {
                val state = categoryViewModel.state.collectAsState().value;
                Dashboard(navController = navController, state = state, onEvent = categoryViewModel::onEvent);
            }
            composable<ItemScreen> { entry ->
                val item = entry.toRoute<ItemScreen>();
                val state = itemViewModel.state.collectAsState().value;
                ItemList(navController = navController, categoryId = item.categoryId, categoryName = item.categoryName, state = state, onEvent = itemViewModel::onEvent, viewModel = itemViewModel);
            }
        }
    }
}
