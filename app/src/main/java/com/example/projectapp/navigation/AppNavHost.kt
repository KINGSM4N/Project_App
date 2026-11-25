package com.example.projectapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectapp.ui.screens.authorization.ForgotPasswordScreen
import com.example.projectapp.ui.screens.authorization.LoginScreen
import com.example.projectapp.ui.screens.authorization.OnboardingScreen
import com.example.projectapp.ui.screens.authorization.SetPasswordScreen
import com.example.projectapp.ui.screens.authorization.SignUpScreen
import com.example.projectapp.ui.screens.authorization.SplashScreen
import com.example.projectapp.ui.screens.home.HomeScreen
import com.example.projectapp.ui.screens.home.DetailsScreen
import com.example.projectapp.ui.screens.setup.SetupActivityScreen
import com.example.projectapp.ui.screens.setup.SetupAgeScreen
import com.example.projectapp.ui.screens.setup.SetupGenderScreen
import com.example.projectapp.ui.screens.setup.SetupGoalScreen
import com.example.projectapp.ui.screens.setup.SetupHeightScreen
import com.example.projectapp.ui.screens.setup.SetupIntroScreen
import com.example.projectapp.ui.screens.setup.SetupProfileScreen
import com.example.projectapp.ui.screens.setup.SetupWeightScreen
import com.example.projectapp.viewmodel.SetupViewModel

@Composable
fun AppNavHost() {
    val nav = rememberNavController()
    val setupViewModel: SetupViewModel = viewModel()
    NavHost(navController = nav, startDestination = Routes.SPLASH) {
//        composable(Routes.SPLASH) {
//            SplashScreen(onFinished = { nav.navigate(Routes.ONBOARDING) })
//        }
//        composable(Routes.ONBOARDING) {
//            OnboardingScreen(onFinished = { nav.navigate(Routes.LOGIN) })
//        }
//        composable(Routes.LOGIN) {
//            LoginScreen(onLoginClick = { nav.navigate(Routes.SETUP_GENDER) })
//        }
//        composable(Routes.LOGIN) {
//            LoginScreen(
//                onLoginClick = { nav.navigate(Routes.SETUP_GENDER) },
//                onSignUpClick = { nav.navigate(Routes.SIGN_UP) },
//                onForgotPasswordClick = { nav.navigate(Routes.FORGOT_PASSWORD) },
//                onBack = { nav.popBackStack() }
//            )
//        }
//        composable(Routes.SIGN_UP) {
//            SignUpScreen(
//                onSignUpSuccess = { nav.navigate(Routes.SETUP_GENDER) },
//                onLoginClick = { nav.popBackStack() },
//                onBack = { nav.popBackStack() }
//            )
//        }
//        composable(Routes.FORGOT_PASSWORD) {
//            ForgotPasswordScreen(
//                onContinueClick = { nav.navigate(Routes.SET_PASSWORD) },
//                onBack = { nav.popBackStack() }
//            )
//        }
//        composable(Routes.SET_PASSWORD) {
//            SetPasswordScreen(
//                onResetClick = {
//                    nav.navigate(Routes.LOGIN) {
//                        popUpTo(Routes.LOGIN) { inclusive = true }
//                    }
//                },
//                onBack = { nav.popBackStack() }
//            )
//        }
//        composable(Routes.SETUP_INTRO) {
//            SetupIntroScreen(onNext = { nav.navigate(Routes.SETUP_GENDER) })
//        }
//        composable(Routes.SETUP_GENDER) {
//            SetupGenderScreen(setupViewModel,
//                onContinue = { nav.navigate(Routes.SETUP_AGE) },
//                onBack = { nav.popBackStack() })
//        }
//        composable(Routes.SETUP_AGE) {
//            SetupAgeScreen(setupViewModel,
//                onContinue = { nav.navigate(Routes.SETUP_WEIGHT) },
//                onBack = { nav.popBackStack() })
//        }
//        composable(Routes.SETUP_WEIGHT) {
//            SetupWeightScreen(setupViewModel,
//                onContinue = { nav.navigate(Routes.SETUP_HEIGHT) },
//                onBack = { nav.popBackStack() })
//        }
//        composable(Routes.SETUP_HEIGHT) {
//            SetupHeightScreen(setupViewModel,
//                onContinue = { nav.navigate(Routes.SETUP_GOAL) },
//                onBack = { nav.popBackStack() })
//        }
//        composable(Routes.SETUP_GOAL) {
//            SetupGoalScreen(setupViewModel,
//                onContinue = { nav.navigate(Routes.SETUP_ACTIVITY) },
//                onBack = { nav.popBackStack() })
//        }
//        composable(Routes.SETUP_ACTIVITY) {
//            SetupActivityScreen(setupViewModel,
//                onContinue = { nav.navigate(Routes.SETUP_PROFILE) },
//                onBack = { nav.popBackStack() })
//        }
        composable(Routes.SETUP_PROFILE) {
            SetupProfileScreen(setupViewModel,
                onStart = {
                    nav.navigate(Routes.HOME) {
                        popUpTo(Routes.SETUP_INTRO) { inclusive = true }
                    }
                },
                onBack = { nav.popBackStack() })
        }
        composable(Routes.HOME) {
            HomeScreen(onItemClick = { id -> nav.navigate(Routes.details(id)) })
        }
    }
}