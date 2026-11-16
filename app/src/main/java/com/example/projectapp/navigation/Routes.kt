package com.example.projectapp.navigation

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val SIGN_UP = "signup"
    const val SETUP_GENDER = "setup_gender"
    const val HOME = "home"
    const val DETAILS = "details/{id}"
    fun details(id: Int) = "details/$id"
}