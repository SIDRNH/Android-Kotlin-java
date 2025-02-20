package com.example.retrofitpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitpractice.di.manual.ManualApplication
import com.example.retrofitpractice.homescreen.presentation.HomeScreen
import com.example.retrofitpractice.homescreen.presentation.HomeScreenState
import com.example.retrofitpractice.homescreen.presentation.HomeScreenViewModel
import com.example.retrofitpractice.ui.theme.RetrofitPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeScreenViewModel: HomeScreenViewModel = viewModel<HomeScreenViewModel>(
                factory = ManualApplication.appModule.homeScreenViewModelFactory
            );
            RetrofitPracticeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state: HomeScreenState by homeScreenViewModel.state.collectAsState();
                    HomeScreen(state = state, onEvent = homeScreenViewModel::onEvent);
                }
            }
        }
    }
}