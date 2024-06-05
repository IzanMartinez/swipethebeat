package com.izamaralv.swipethebeat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.izamaralv.swipethebeat.common.Constants.SCREENS
import com.izamaralv.swipethebeat.common.darkComponentColor
import com.izamaralv.swipethebeat.common.lightComponentColor
import com.izamaralv.swipethebeat.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CustomDrawerSheet(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    backgroundColor: Color
) {
    // item seleccionado (default 0)
    var selectedScreen = remember { mutableStateOf(SCREENS[0]) }

    ModalDrawerSheet {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            // item pantalla principal
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Screen.HomeScreen.icon, // Use the main screen icon
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(text = "Inicio") },
                selected = SCREENS[0] == selectedScreen.value, // (optional) highlight if needed
                onClick = {
                    // Navigate to Main Screen
                    navController.navigate(Screen.HomeScreen.route)
                    scope.launch { drawerState.close() } // Close drawer after navigation

                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = lightComponentColor,
                    unselectedContainerColor = darkComponentColor
                )
            )
        }
    }
}

@Composable
fun CustomDrawerSettingsSheet(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    backgroundColor: Color
) {
    // item seleccionado (default 0)
    var selectedScreen = remember { mutableStateOf(SCREENS[0]) }

    ModalDrawerSheet {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            // item pantalla principal
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Screen.HomeScreen.icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(text = "Inicio") },
                selected = SCREENS[0] == selectedScreen.value,
                onClick = {
                    navController.navigate(Screen.HomeScreen.route)
                    scope.launch { drawerState.close() }
                }
            )
        }
    }
}



