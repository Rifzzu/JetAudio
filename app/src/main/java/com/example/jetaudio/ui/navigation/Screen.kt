package com.example.jetaudio.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object Favorite : Screen("favorite")
    object Detail : Screen("home/{audioId}") {
        fun createRoute(audioId: Long) = "home/$audioId"
    }
}