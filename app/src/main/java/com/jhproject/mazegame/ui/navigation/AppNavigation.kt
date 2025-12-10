package com.jhproject.mazegame.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhproject.mazegame.ui.login.LoginScreen
import com.jhproject.mazegame.ui.registration.RegistrationScreen

enum class AppScreen(val route: String) {
    LOGIN_SCREEN("login_screen"),
    REGISTRATION_SCREEN("registration_screen"),
}

@Composable
fun AppNavigation() {
    val appNavController = rememberNavController()

    NavHost(
        navController = appNavController,
        startDestination = AppScreen.LOGIN_SCREEN.route
    ) {
        composable(AppScreen.LOGIN_SCREEN.route) {
            LoginScreen(
                navigateToRegistration = { appNavController.navigate(AppScreen.REGISTRATION_SCREEN.route) }
            )
        }
        composable(AppScreen.REGISTRATION_SCREEN.route) {
            RegistrationScreen(
                navigateToLogin = { appNavController.navigate(AppScreen.LOGIN_SCREEN.route) }
            )
        }
    }
}