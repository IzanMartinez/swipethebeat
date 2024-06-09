package com.izamaralv.swipethebeat.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String = "",
    val icon: ImageVector = Icons.Default.Abc
) {

    object RegisterScreen : Screen(route = "register")

    object LoginScreen : Screen(route = "login")

    object SettingsScreen : Screen(route = "settings_screen", title = "Settings", icon = Icons.Default.Settings)

    object HomeScreen : Screen(route = "home_screen", title = "Settings", icon = Icons.Default.Home)

    object ListsScreen : Screen(route = "lists_screen", title = "Lists", Icons.Default.Audiotrack)

    object ProfileScreen : Screen(route = "profile_screen", title = "Profile", Icons.Default.Person)

    object LikeScreen : Screen(route = "like_screen", title = "Like", Icons.Default.Check)

    object DislikeScreen : Screen(route = "dislike_screen", title = "Dislike", Icons.Default.Clear)

}