package com.jhproject.mazegame.ui.navigation

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jhproject.mazegame.data.ChildLandingScreenViewModelFactory
import com.jhproject.mazegame.data.LoginViewModelFactory
import com.jhproject.mazegame.data.ParentLandingScreenViewModelFactory
import com.jhproject.mazegame.data.RegistrationViewModelFactory
import com.jhproject.mazegame.ui.childlandingscreen.ChildLandingScreen
import com.jhproject.mazegame.ui.childlandingscreen.ChildLandingScreenViewModel
import com.jhproject.mazegame.ui.easylevels.EasyLevel1Screen
import com.jhproject.mazegame.ui.easylevels.EasyLevel1ScreenViewModel
import com.jhproject.mazegame.ui.easylevels.EasyLevel1ViewModelFactory
import com.jhproject.mazegame.ui.easylevels.EasyLevel2Screen
import com.jhproject.mazegame.ui.easylevels.EasyLevel2ScreenViewModel
import com.jhproject.mazegame.ui.easylevels.EasyLevel2ViewModelFactory
import com.jhproject.mazegame.ui.easylevels.EasyLevel3Screen
import com.jhproject.mazegame.ui.easylevels.EasyLevel3ScreenViewModel
import com.jhproject.mazegame.ui.easylevels.EasyLevel3ViewModelFactory
import com.jhproject.mazegame.ui.hardlevels.HardLevel1Screen
import com.jhproject.mazegame.ui.hardlevels.HardLevel1ScreenViewModel
import com.jhproject.mazegame.ui.hardlevels.HardLevel1ViewModelFactory
import com.jhproject.mazegame.ui.hardlevels.HardLevel2Screen
import com.jhproject.mazegame.ui.hardlevels.HardLevel2ScreenViewModel
import com.jhproject.mazegame.ui.hardlevels.HardLevel2ViewModelFactory
import com.jhproject.mazegame.ui.hardlevels.HardLevel3Screen
import com.jhproject.mazegame.ui.hardlevels.HardLevel3ScreenViewModel
import com.jhproject.mazegame.ui.hardlevels.HardLevel3ViewModelFactory
import com.jhproject.mazegame.ui.login.LoginScreen
import com.jhproject.mazegame.ui.login.LoginScreenViewModel
import com.jhproject.mazegame.ui.parentlandingscreen.ParentLandingScreen
import com.jhproject.mazegame.ui.parentlandingscreen.ParentLandingScreenViewModel
import com.jhproject.mazegame.ui.registration.RegistrationScreen
import com.jhproject.mazegame.ui.registration.RegistrationScreenViewModel

enum class AppScreen(val route: String) {
    LOGIN_SCREEN("login_screen"),
    REGISTRATION_SCREEN("registration_screen"),
    PARENT_SCREEN("parent_screen/{userId}"),
    CHILD_SCREEN("child_screen/{childId}"),
    EASYLEVEL_1_SCREEN("easylevel_1_screen/{childId}"),
    EASYLEVEL_2_SCREEN("easylevel_2_screen/{childId}"),
    EASYLEVEL_3_SCREEN("easylevel_3_screen/{childId}"),
    HARDLEVEL_1_SCREEN("hardlevel_1_screen/{childId}"),
    HARDLEVEL_2_SCREEN("hardlevel_2_screen/{childId}"),
    HARDLEVEL_3_SCREEN("hardlevel_3_screen/{childId}")
}

fun navigateToParentScreen(userId: Int) = "parent_screen/$userId"

fun navigateToChildScreen(childId: Int) = "child_screen/$childId"

fun navigateToEasyLevel1Screen(childId: Int) = "easylevel_1_screen/$childId"

fun navigateToEasyLevel2Screen(childId: Int) = "easylevel_2_screen/$childId"

fun navigateToEasyLevel3Screen(childId: Int) = "easylevel_3_screen/$childId"

fun navigateToHardLevel1Screen(childId: Int) = "hardlevel_1_screen/$childId"

fun navigateToHardLevel2Screen(childId: Int) = "hardlevel_2_screen/$childId"

fun navigateToHardLevel3Screen(childId: Int) = "hardlevel_3_screen/$childId"



@Composable
fun AppNavigation() {
    val appNavController = rememberNavController()

    val context = LocalContext.current.applicationContext as Application

    val registrationViewModel: RegistrationScreenViewModel = viewModel(
        factory = RegistrationViewModelFactory(context)
    )

    val loginViewModel: LoginScreenViewModel = viewModel(
        factory = LoginViewModelFactory(context)
    )

    NavHost(
        navController = appNavController,
        startDestination = AppScreen.LOGIN_SCREEN.route
    ) {
        composable(AppScreen.LOGIN_SCREEN.route) {
            LoginScreen(
                viewModel = loginViewModel,
                navigateToRegistration = { appNavController.navigate(AppScreen.REGISTRATION_SCREEN.route) },
                navigateToParentLanding = { userId -> appNavController.navigate(navigateToParentScreen(userId)) },
                navigateToChildLanding = { childId -> appNavController.navigate(navigateToChildScreen(childId)) }
            )
        }
        composable(AppScreen.REGISTRATION_SCREEN.route) {
            RegistrationScreen(
                viewModel = registrationViewModel,
                navigateToLogin = { appNavController.navigate(AppScreen.LOGIN_SCREEN.route) }
            )
        }
        composable(
            route = AppScreen.PARENT_SCREEN.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType } // Define the argument type
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId")
            if (userId != null) {
                val factory = ParentLandingScreenViewModelFactory(context, backStackEntry.savedStateHandle)
                val parentLandingViewModel: ParentLandingScreenViewModel = viewModel(factory = factory)
                ParentLandingScreen(viewModel = parentLandingViewModel)
            } else {
                Toast.makeText(
                    context,
                    "Invalid username or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        composable(
            route = AppScreen.CHILD_SCREEN.route,
            arguments = listOf(
                navArgument("childId") { type = NavType.IntType } // Define the argument type
            )
        ) { backStackEntry ->
            val childId = backStackEntry.arguments?.getInt("childId")
            if (childId != null) {
                val factory = ChildLandingScreenViewModelFactory(context, backStackEntry.savedStateHandle)
                val childLandingViewModel: ChildLandingScreenViewModel = viewModel(factory = factory)
                ChildLandingScreen(viewModel = childLandingViewModel,
                    navigateToEasyLevel1 = { appNavController.navigate(navigateToEasyLevel1Screen(childId)) },
                    navigateToEasyLevel2 = { appNavController.navigate(navigateToEasyLevel2Screen(childId)) },
                    navigateToEasyLevel3 = { appNavController.navigate(navigateToEasyLevel3Screen(childId)) },
                    navigateToHardLevel1 = { appNavController.navigate(navigateToHardLevel1Screen(childId)) },
                    navigateToHardLevel2 = { appNavController.navigate(navigateToHardLevel2Screen(childId)) },
                    navigateToHardLevel3 = { appNavController.navigate(navigateToHardLevel3Screen(childId)) }
                )
            } else {
                Toast.makeText(
                    context,
                    "Invalid username or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        composable(
            route = AppScreen.EASYLEVEL_1_SCREEN.route,
            arguments = listOf(
                navArgument("childId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val childId = backStackEntry.arguments?.getInt("childId")
            if (childId != null) {
                val factory = EasyLevel1ViewModelFactory(context, backStackEntry.savedStateHandle)
                val level1ViewModel: EasyLevel1ScreenViewModel = viewModel(factory = factory)
                EasyLevel1Screen(viewModel = level1ViewModel, onBack = { appNavController.popBackStack() })
            } else {
                Toast.makeText(
                    context,
                    "Error, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        composable(
            route = AppScreen.EASYLEVEL_2_SCREEN.route,
            arguments = listOf(
                navArgument("childId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val childId = backStackEntry.arguments?.getInt("childId")
            if (childId != null) {
                val factory = EasyLevel2ViewModelFactory(context, backStackEntry.savedStateHandle)
                val level2ViewModel: EasyLevel2ScreenViewModel = viewModel(factory = factory)
                EasyLevel2Screen(viewModel = level2ViewModel, onBack = { appNavController.popBackStack() })
            } else {
                Toast.makeText(
                    context,
                    "Error, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        composable(
            route = AppScreen.EASYLEVEL_3_SCREEN.route,
            arguments = listOf(
                navArgument("childId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val childId = backStackEntry.arguments?.getInt("childId")
            if (childId != null) {
                val factory = EasyLevel3ViewModelFactory(context, backStackEntry.savedStateHandle)
                val level3ViewModel: EasyLevel3ScreenViewModel = viewModel(factory = factory)
                EasyLevel3Screen(viewModel = level3ViewModel, onBack = { appNavController.popBackStack() })
            } else {
                Toast.makeText(
                    context,
                    "Error, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        composable(
            route = AppScreen.HARDLEVEL_1_SCREEN.route,
            arguments = listOf(
                navArgument("childId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val childId = backStackEntry.arguments?.getInt("childId")
            if (childId != null) {
                val factory = HardLevel1ViewModelFactory(context, backStackEntry.savedStateHandle)
                val level1ViewModel: HardLevel1ScreenViewModel = viewModel(factory = factory)
                HardLevel1Screen(viewModel = level1ViewModel, onBack = { appNavController.popBackStack() })
            } else {
                Toast.makeText(
                    context,
                    "Error, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        composable(
            route = AppScreen.HARDLEVEL_2_SCREEN.route,
            arguments = listOf(
                navArgument("childId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val childId = backStackEntry.arguments?.getInt("childId")
            if (childId != null) {
                val factory = HardLevel2ViewModelFactory(context, backStackEntry.savedStateHandle)
                val level2ViewModel: HardLevel2ScreenViewModel = viewModel(factory = factory)
                HardLevel2Screen(viewModel = level2ViewModel, onBack = { appNavController.popBackStack() })
            } else {
                Toast.makeText(
                    context,
                    "Error, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        composable(
            route = AppScreen.HARDLEVEL_3_SCREEN.route,
            arguments = listOf(
                navArgument("childId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val childId = backStackEntry.arguments?.getInt("childId")
            if (childId != null) {
                val factory = HardLevel3ViewModelFactory(context, backStackEntry.savedStateHandle)
                val level3ViewModel: HardLevel3ScreenViewModel = viewModel(factory = factory)
                HardLevel3Screen(viewModel = level3ViewModel, onBack = { appNavController.popBackStack() })
            } else {
                Toast.makeText(
                    context,
                    "Error, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}