package com.example.yeschat.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeschat.HomeScreen
import com.example.yeschat.Login
import com.example.yeschat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    LaunchedEffect(Unit) {
        if (user != null) {
            delay(1000);
            navController.navigate(HomeScreen) {
                popUpTo(0);
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        if (user != null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(500.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo"
                );
            };
        } else {
            Column(
                modifier = Modifier.fillMaxSize().background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo"
                );
                Spacer(modifier = Modifier.height(250.dp))
                Button(
                    onClick = {
                        navController.navigate(Login) {
                            popUpTo(0);
                        }
                    },
                    content = { Text(text = "Login") },
                    shape = RoundedCornerShape(3.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen(rememberNavController());
}