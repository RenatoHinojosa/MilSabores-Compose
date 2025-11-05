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
import com.example.milsaborescompose.data.local.User
import com.example.milsaborescompose.viewmodel.UserViewModel
import com.example.milsaborescompose.viewmodel.ViewModelFactory

@Composable
fun MainScreen(viewModelFactory: ViewModelFactory) {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel(factory = viewModelFactory)
    val loggedInUserId by userViewModel.loggedInUserId.collectAsState()

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
                    onCategoryClick = { categoryName ->
                        navController.navigate("category/$categoryName")
                    },
                    onAddProductClick = { navController.navigate("product_management") }
                )
            }
            composable(Screen.Cart.route) { CartScreen(navController = navController, viewModelFactory = viewModelFactory) }
            composable(Screen.Profile.route) {
                if (loggedInUserId != null) {
                    ProfileScreen(userViewModel = userViewModel, onLogout = { userViewModel.logout() })
                } else {
                    UserScreen(
                        viewModel = userViewModel,
                        onSignUpClicked = { navController.navigate("signup") },
                        onLoginClicked = { navController.navigate("login") }
                    )
                }
            }
            composable("signup") {
                SignUpScreen(
                    onSignUp = { nombre, correo, contrasena, telefono, metodoDePago ->
                        userViewModel.insertUser(User(nombre = nombre, correo = correo, contrasena = contrasena, telefono = telefono, metodoDePago = metodoDePago))
                        navController.navigate(Screen.Profile.route) { popUpTo(Screen.Profile.route) { inclusive = true } }
                    },
                    navController = navController
                )
            }
            composable("login") {
                LoginScreen(
                    onLogin = { email, password ->
                        userViewModel.login(email, password) { success ->
                            if (success) {
                                navController.navigate(Screen.Profile.route) { popUpTo(Screen.Profile.route) { inclusive = true } }
                            }
                        }
                    },
                    onLoginSuccess = {
                        navController.navigate(Screen.Profile.route) { popUpTo(Screen.Profile.route) { inclusive = true } }
                    },
                    navController = navController
                )
            }
            composable(
                route = "category/{categoryName}",
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName")
                if (categoryName != null) {
                    CategoryProductsScreen(
                        categoryName = categoryName,
                        viewModelFactory = viewModelFactory,
                        navController = navController,
                        onProductClick = { productId ->
                            navController.navigate("product/$productId")
                        }
                    )
                }
            }
            composable(
                route = "product/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId")
                if (productId != null) {
                    ProductDetailScreen(
                        productId = productId,
                        viewModelFactory = viewModelFactory,
                        navController = navController
                    )
                }
            }
             composable("product_management") {
                val productViewModel: com.example.milsaborescompose.viewmodel.ProductViewModel = viewModel(factory = viewModelFactory)
                ProductScreen(viewModel = productViewModel)
            }
        }
    }
}