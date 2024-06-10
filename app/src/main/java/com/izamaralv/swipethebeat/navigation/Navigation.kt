package com.izamaralv.swipethebeat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.izamaralv.swipethebeat.screens.MainScreen
import com.izamaralv.swipethebeat.screens.ProfileScreen
import com.izamaralv.swipethebeat.screens.SettingsScreen
import com.izamaralv.swipethebeat.screens.credentials.LoginScreen
import com.izamaralv.swipethebeat.screens.credentials.RegisterScreen
import com.izamaralv.swipethebeat.screens.lists.DislikeScreen
import com.izamaralv.swipethebeat.screens.lists.LikeScreen
import com.izamaralv.swipethebeat.screens.lists.ListsScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(Screen.SettingsScreen.route) {
            SettingsScreen(navController)
        }
        composable(Screen.HomeScreen.route) {
            MainScreen(navController = navController)
        }
        composable(Screen.ListsScreen.route) {
            ListsScreen(navController)
        }
        composable(Screen.LikeScreen.route) {
            LikeScreen(navController)
        }
        composable(Screen.DislikeScreen.route) {
            DislikeScreen(navController)
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}
