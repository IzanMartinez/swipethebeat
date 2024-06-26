package com.izamaralv.swipethebeat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.izamaralv.swipethebeat.common.Constants.SCREENS
import com.izamaralv.swipethebeat.common.colorName
import com.izamaralv.swipethebeat.common.contentColor
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
    // colors
    val darkComponentColor by darkComponentColor
    val lightComponentColor by lightComponentColor
    val contentColor by contentColor
    val colorName by colorName

    // item seleccionado (default 0)
    var selectedScreen = remember { mutableStateOf(SCREENS[0]) }

    ModalDrawerSheet {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Column {
                Spacer(modifier = Modifier.size(20.dp))
                // item pantalla principal
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Screen.HomeScreen.icon, // Use the main screen icon
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(text = "Inicio", color = contentColor) },
                    selected = SCREENS[0] == selectedScreen.value,
                    onClick = {
                        // Navigate to Main Screen
                        navController.navigate(Screen.HomeScreen.route)
                        scope.launch { drawerState.close() } // Close drawer after navigation
                        selectedScreen.value = SCREENS[0] // Update selected screen
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = darkComponentColor,
                        unselectedContainerColor = darkComponentColor
                    )
                )

                Spacer(modifier = Modifier.size(20.dp))

                // item pantalla de listas
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Screen.ListsScreen.icon, // Use an appropriate icon for profile
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(text = "Tus Listas", color = contentColor) },
                    selected = SCREENS[0] == selectedScreen.value,
                    onClick = {
                        // Navigate to Profile Screen
                        navController.navigate(Screen.ListsScreen.route)
                        scope.launch { drawerState.close() } // Close drawer after navigation
                        selectedScreen.value = SCREENS[0] // Update selected screen
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = darkComponentColor,
                        unselectedContainerColor = darkComponentColor
                    )
                )

                Spacer(modifier = Modifier.size(20.dp))

                // item pantalla de perfil
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Screen.ProfileScreen.icon, // Use an appropriate icon for profile
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(text = "Perfil", color = contentColor) },
                    selected = SCREENS[0] == selectedScreen.value,
                    onClick = {
                        // Navigate to Profile Screen
                        navController.navigate(Screen.ProfileScreen.route)
                        scope.launch { drawerState.close() } // Close drawer after navigation
                        selectedScreen.value = SCREENS[0] // Update selected screen
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = darkComponentColor,
                        unselectedContainerColor = darkComponentColor
                    )
                )

                Spacer(modifier = Modifier.size(20.dp))


            }
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



