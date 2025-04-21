package com.example.retrofitpractice.splashscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.retrofitpractice.R
import com.example.retrofitpractice.navigation.HomeScreen
import com.example.retrofitpractice.navigation.SplashScreen
import com.example.retrofitpractice.navigation.UserLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser;

    LaunchedEffect(true) {
        delay(3000L);
        if (user == null) {
            navController.navigate(UserLogin) {
                popUpTo(SplashScreen) { inclusive = true }
            }
        } else {
            navController.navigate(HomeScreen) {
                popUpTo(SplashScreen) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.app_name))
    }
}