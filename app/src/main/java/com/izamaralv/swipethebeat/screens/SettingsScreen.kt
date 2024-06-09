package com.izamaralv.swipethebeat.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.izamaralv.swipethebeat.common.Constants.resetLists
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.colorName
import com.izamaralv.swipethebeat.common.contentColor
import com.izamaralv.swipethebeat.common.darkComponentColor
import com.izamaralv.swipethebeat.common.lightComponentColor
import com.izamaralv.swipethebeat.components.CustomDrawerSheet
import com.izamaralv.swipethebeat.components.CustomLogoInBox
import com.izamaralv.swipethebeat.navigation.Screen
import com.izamaralv.swipethebeat.ui.theme.BlueName
import com.izamaralv.swipethebeat.ui.theme.DarkName
import com.izamaralv.swipethebeat.ui.theme.GreenName
import com.izamaralv.swipethebeat.ui.theme.HighContrastName
import com.izamaralv.swipethebeat.ui.theme.LightName
import com.izamaralv.swipethebeat.ui.theme.OrangeName
import com.izamaralv.swipethebeat.ui.theme.PurpleName
import com.izamaralv.swipethebeat.ui.theme.RedName
import com.izamaralv.swipethebeat.utils.changeTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController) {

    // colors
    val backgroundColor by backgroundColor
    val darkComponentColor by darkComponentColor
    val lightComponentColor by lightComponentColor
    val contentColor by contentColor
    val colorName by colorName

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val resetListsDialogState = remember { mutableStateOf(false) }
    val logoutDialogState = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var expandedColor by remember { mutableStateOf(false) }
    var recompositionTrigger by remember { mutableIntStateOf(0) }


    Column(
        modifier = Modifier
            .background(color = backgroundColor)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ModalNavigationDrawer(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            drawerContent = {
                CustomDrawerSheet(
                    navController = navController,
                    drawerState = drawerState,
                    scope = scope,
                    backgroundColor = darkComponentColor
                )
            },
            drawerState = drawerState,
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp, start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null,
                                tint = contentColor,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomLogoInBox()
                    }
                    Box(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = null,
                                tint = backgroundColor.copy(alpha = 0f),
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.size(40.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Paleta de colores: ",
                        color = contentColor,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Box(Modifier.width(150.dp)) {
                        Text(
                            text = colorName,
                            color = contentColor,
                            fontSize = 25.sp,
                            modifier = Modifier
                                .clickable { expandedColor = true }
                                .background(darkComponentColor)
                                .padding(16.dp)
                                .fillMaxWidth()
                        )
                        DropdownMenu(
                            expanded = expandedColor,
                            onDismissRequest = { expandedColor = false },
                            modifier = Modifier.background(darkComponentColor)
                        ) {
                            // green
                            DropdownMenuItem(
                                text = { Text(text = GreenName, color = contentColor) },
                                onClick = {
                                    expandedColor = false
                                    changeTheme(GreenName)

                                    recompositionTrigger++ // Trigger recomposition

                                }
                            )
                            // blue
                            DropdownMenuItem(
                                text = { Text(text = BlueName, color = contentColor) },
                                onClick = {
                                    expandedColor = false
                                    changeTheme(BlueName)

                                    recompositionTrigger++ // Trigger recomposition

                                }
                            )
                            // orange
                            DropdownMenuItem(
                                text = { Text(text = OrangeName, color = contentColor) },
                                onClick = {
                                    expandedColor = false
                                    changeTheme(OrangeName)

                                    recompositionTrigger++ // Trigger recomposition

                                }
                            )
                            // red
                            DropdownMenuItem(
                                text = { Text(text = RedName, color = contentColor) },
                                onClick = {
                                    expandedColor = false
                                    changeTheme(RedName)

                                    recompositionTrigger++ // Trigger recomposition

                                }
                            )
                            // purple
                            DropdownMenuItem(
                                text = { Text(text = PurpleName, color = contentColor) },
                                onClick = {
                                    expandedColor = false
                                    changeTheme(PurpleName)
                                }
                            )
                            // light
                            DropdownMenuItem(
                                text = { Text(text = LightName, color = contentColor) },
                                onClick = {
                                    expandedColor = false
                                    changeTheme(LightName)

                                    recompositionTrigger++ // Trigger recomposition

                                }
                            )
                            // dark
                            DropdownMenuItem(
                                text = { Text(text = DarkName, color = contentColor) },
                                onClick = {
                                    expandedColor = false
                                    changeTheme(DarkName)

                                    recompositionTrigger++ // Trigger recomposition

                                }
                            )
                            // high contrast
                            DropdownMenuItem(
                                text = { Text(text = HighContrastName, color = contentColor) },
                                onClick = {
                                    expandedColor = false
                                    changeTheme(HighContrastName)

                                    recompositionTrigger++ // Trigger recomposition
                                }
                            )

                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Resetear listas",
                        modifier = Modifier
                            .clickable {
                                resetListsDialogState.value = true
                            }
                            .padding(top = 15.dp),
                        color = contentColor,
                        fontSize = 25.sp,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Cerrar sesión",
                        modifier = Modifier
                            .clickable {
                                logoutDialogState.value = true
                            }
                            .padding(top = 15.dp),
                        color = contentColor,
                        fontSize = 25.sp,
                    )
                }
            }
        }
    }

    if (resetListsDialogState.value) {
        AlertDialog(
            title = { Text("Alerta") },
            text = { Text("¿Estás seguro de que quieres resetear tus listas?") },
            containerColor = darkComponentColor,
            textContentColor = contentColor,
            iconContentColor = contentColor,
            titleContentColor = contentColor,
            onDismissRequest = { /* do nothing */ },
            confirmButton = {
                TextButton(
                    onClick = {
                        resetLists()
                        resetListsDialogState.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lightComponentColor,
                        contentColor = contentColor
                    )
                ) {
                    Text(text = "Si")

                }
            },
            dismissButton = {
                TextButton(
                    onClick = { resetListsDialogState.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lightComponentColor,
                        contentColor = contentColor
                    )
                ) {
                    Text(
                        text = "No",
                    )
                }
            },
        )

    }
    if (logoutDialogState.value) {
        AlertDialog(
            title = { Text("Alerta") },
            text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
            containerColor = darkComponentColor,
            textContentColor = contentColor,
            iconContentColor = contentColor,
            titleContentColor = contentColor,
            onDismissRequest = { /* do nothing */ },
            confirmButton = {
                TextButton(
                    onClick = {
                        navController.navigate(Screen.LoginScreen.route)
                        resetListsDialogState.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lightComponentColor,
                        contentColor = contentColor
                    )
                ) {
                    Text(text = "Si")

                }
            },
            dismissButton = {
                TextButton(
                    onClick = { resetListsDialogState.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lightComponentColor,
                        contentColor = contentColor
                    )
                ) {
                    Text(
                        text = "No",
                    )
                }
            },
        )

    }
}
