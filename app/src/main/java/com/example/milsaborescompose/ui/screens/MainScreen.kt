package com.example.milsaborescompose.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.milsaborescompose.viewmodel.UserViewModel
import com.example.milsaborescompose.viewmodel.ViewModelFactory

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModelFactory = remember { ViewModelFactory(context) }

    val screens = listOf(
        Screen.Home,
        Screen.Catalog,
        Screen.Cart,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.tertiary) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { HomeScreen() }

            composable(Screen.Catalog.route) {
                CatalogScreen(
                    viewModelFactory = viewModelFactory,
                    onCategoryClick = { categoryId, categoryName ->
                        navController.navigate("category/$categoryId/$categoryName")
                    }
                )
            }

            composable(Screen.Cart.route) { CartScreen(navController = navController, viewModelFactory = viewModelFactory) }

            composable(Screen.Profile.route) {
                val userViewModel: UserViewModel = viewModel(factory = viewModelFactory)
                val uiState by userViewModel.uiState.collectAsState()
                
                ProfileScreen(
                    uiState = uiState,
                    onLogout = { userViewModel.logout() },
                    onLogoutSuccess = { navController.navigate(Screen.Home.route){ popUpTo(Screen.Home.route){ inclusive = true } } },
                    navController = navController
                )
            }

            composable("signup") {
                val userViewModel: UserViewModel = viewModel(factory = viewModelFactory)
                val paymentViewModel: com.example.milsaborescompose.viewmodel.PaymentMethodViewModel = viewModel(factory = viewModelFactory)

                SignUpScreen(
                    userUiState = userViewModel.uiState.collectAsState().value,
                    paymentMethodUiState = paymentViewModel.uiState.collectAsState().value,
                    onRegister = { registerRequest -> userViewModel.register(registerRequest) },
                    onRegistrationSuccess = { navController.navigate(Screen.Profile.route) { popUpTo(Screen.Profile.route) { inclusive = true } } },
                    onErrorDismiss = { userViewModel.clearRegistrationError() },
                    navController = navController
                )
            }

            composable("login") {
                val userViewModel: UserViewModel = viewModel(factory = viewModelFactory)
                LoginScreen(
                    uiState = userViewModel.uiState.collectAsState().value,
                    onLogin = { loginRequest -> userViewModel.login(loginRequest) },
                    onLoginSuccess = { navController.navigate(Screen.Profile.route) { popUpTo(Screen.Profile.route) { inclusive = true } } },
                    onErrorDismiss = { userViewModel.clearLoginError() },
                    navController = navController
                )
            }

            composable(
                route = "category/{categoryId}/{categoryName}",
                arguments = listOf(
                    navArgument("categoryId") { type = NavType.LongType },
                    navArgument("categoryName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getLong("categoryId") ?: 0L
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                CategoryProductsScreen(
                    categoryId = categoryId,
                    categoryName = categoryName,
                    viewModelFactory = viewModelFactory,
                    navController = navController,
                    onProductClick = { productId ->
                        navController.navigate("product/$productId")
                    }
                )
            }

            composable(
                route = "product/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.LongType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getLong("productId") ?: 0L
                ProductDetailScreen(
                    productId = productId,
                    viewModelFactory = viewModelFactory,
                    navController = navController
                )
            }

            composable("product_management") {
                val productViewModel: com.example.milsaborescompose.viewmodel.ProductViewModel = viewModel(factory = viewModelFactory)
                ProductScreen(viewModel = productViewModel)
            }
        }
    }
}