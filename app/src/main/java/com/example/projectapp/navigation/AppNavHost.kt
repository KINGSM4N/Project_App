package com.example.projectapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectapp.ui.screens.authorization.LoginScreen
import com.example.projectapp.ui.screens.authorization.OnboardingScreen
import com.example.projectapp.ui.screens.authorization.SplashScreen
import com.example.projectapp.ui.screens.home.HomeScreen
import com.example.projectapp.ui.screens.home.DetailsScreen
import com.example.projectapp.ui.screens.setup.SetupGenderScreen

@Composable
fun AppNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onFinished = { nav.navigate(Routes.ONBOARDING) })
        }
        composable(Routes.ONBOARDING) {
            OnboardingScreen(onFinished = { nav.navigate(Routes.LOGIN) })
        }
        composable(Routes.LOGIN) {
            LoginScreen(onLoginClick = { nav.navigate(Routes.SETUP_GENDER) })
        }
//        composable(Routes.SETUP_GENDER) {
//            SetupGenderScreen(onContinue = { nav.navigate(Routes.HOME) })
//        }
//        composable(Routes.HOME) {
//            HomeScreen(onItemClick = { id -> nav.navigate(Routes.details(id)) })
//        }
//        composable(
//            route = Routes.DETAILS,
//            arguments = listOf(navArgument("id") { type = NavType.IntType })
//        ) { entry ->
//            val id = entry.arguments?.getInt("id") ?: 0
//            DetailsScreen(id = id, onBack = { nav.popBackStack() })
//        }
    }
}