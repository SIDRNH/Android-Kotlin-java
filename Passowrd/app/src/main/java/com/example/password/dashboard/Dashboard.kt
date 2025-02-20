package com.example.password.dashboard

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.password.authentication.AuthenticationViewModel
import com.example.password.components.AddCategory
import com.example.password.components.Category
import com.example.password.components.DropDownCategory
import com.example.password.navigation.ItemScreen
import com.example.password.navigation.UserLoginScreen
import com.example.password.objects.category.CategoryEvent
import com.example.password.objects.category.CategoryState
import com.example.password.ui.theme.PaleCornFlowerBlue
import com.example.password.ui.theme.PasswordTheme
import com.example.password.ui.theme.SquidInk

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavController, state: CategoryState, onEvent: (CategoryEvent) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior();
    val topAppbarColor = if (isSystemInDarkTheme()) SquidInk else PaleCornFlowerBlue;
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel<AuthenticationViewModel>();

    PasswordTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text("Dashboard");
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = topAppbarColor
                    ),
                    actions = {
                        IconButton(onClick = {
                            authenticationViewModel.signOut();
                            navController.navigate(UserLoginScreen)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = "Sign Out"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onEvent(CategoryEvent.ShowDialog)
                    },
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Category"
                    );
                }
            }
        ) {innerPadding ->
            if (state.categories.isEmpty()) {
                Box(
                    modifier = Modifier.padding(innerPadding).fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Categories available");
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.categories) { category ->
                        Category(
                            onClick = {navController.navigate(ItemScreen(categoryId = category.id, categoryName = category.name))},
                            itemName = category.name,
                            dropDownCategories = listOf<DropDownCategory>(DropDownCategory(text = "Delete", onClick = {onEvent(CategoryEvent.DeleteCategory(category = category))}), DropDownCategory(text = "Edit", onClick = {onEvent(CategoryEvent.EditCategory(category = category))}))
                        )
                    }
                }
            }
            if (state.isAddingCategory) {
                AddCategory(state = state, onEvent = onEvent)
            }
        }
    }
}

