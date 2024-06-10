package com.izamaralv.swipethebeat.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.colorName
import com.izamaralv.swipethebeat.common.contentColor
import com.izamaralv.swipethebeat.common.darkComponentColor
import com.izamaralv.swipethebeat.common.lightComponentColor
import com.izamaralv.swipethebeat.components.CustomDrawerSheet
import com.izamaralv.swipethebeat.components.CustomLogoInBox
import com.izamaralv.swipethebeat.model.DataViewModel
import com.izamaralv.swipethebeat.navigation.Screen
import com.izamaralv.swipethebeat.utils.countSongsInUserDislikeList
import com.izamaralv.swipethebeat.utils.countSongsInUserLikeList
import com.izamaralv.swipethebeat.utils.getEmail
import com.izamaralv.swipethebeat.utils.getUsernameByEmail
import com.izamaralv.swipethebeat.utils.updateUsernameInFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, dataViewModel: DataViewModel = viewModel()) {
    // colors
    val backgroundColor by backgroundColor
    val darkComponentColor by darkComponentColor
    val lightComponentColor by lightComponentColor
    val contentColor by contentColor
    val colorName by colorName

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val email: String = getEmail() ?: "stbexample@gmail.com"
    var username by remember { mutableStateOf("") }
    var likeCount by remember { mutableStateOf(0) }
    var dislikeCount by remember { mutableStateOf(0) }
    var editingUsername by remember { mutableStateOf(false) }
    var newUsername by remember { mutableStateOf("") }

    LaunchedEffect(email) {
        Log.d("ProfileScreen", "Fetching username for email: $email")
        val fetchedUsername = getUsernameByEmail(email)
        if (fetchedUsername != null) {
            Log.d("ProfileScreen", "Fetched username: $fetchedUsername")
            username = fetchedUsername
        } else {
            Log.d("ProfileScreen", "Username not found for email: $email")
        }

        // Fetch and set the count of liked songs
        likeCount = countSongsInUserLikeList(email)
        Log.d("ProfileScreen", "Fetched likeCount: $likeCount")

        // Fetch and set the count of disliked songs
        dislikeCount = countSongsInUserDislikeList(email)
        Log.d("ProfileScreen", "Fetched dislikeCount: $dislikeCount")
    }

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
            drawerState = drawerState
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // botón menu
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
                // logo
                Box(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {
                    CustomLogoInBox()
                }
                // botón settings
                Box(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { navController.navigate(Screen.SettingsScreen.route) }
                    ) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 190.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                // muestra de usuario
                Text(
                    text = "Nombre de usuario: ",
                    color = contentColor,
                    fontSize = 15.sp
                )
                if (editingUsername) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Text input for editing username
                        OutlinedTextField(
                            value = newUsername,
                            onValueChange = { newUsername = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Nuevo nombre de usuario") },
                            singleLine = true,
                            textStyle = TextStyle(color = contentColor),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = contentColor,
                                unfocusedBorderColor = contentColor
                            )
                        )
                        // Button to confirm username change
                        IconButton(
                            onClick = {
                                scope.launch {
                                    updateUsernameInFirestore(email, newUsername)
                                    username = newUsername
                                    editingUsername = false
                                }
                            }
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Confirm")
                        }
                        // Button to cancel editing
                        IconButton(
                            onClick = { editingUsername = false }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel")
                        }
                    }
                } else {
                    // Display current username
                    Text(
                        text = username,
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        fontSize = 20.sp,
                        modifier = Modifier.clickable { editingUsername = true }
                    )
                }
                // espaciado
                Text("\n")
                // muestra de email
                Text(
                    text = "Correo electrónico: ",
                    color = contentColor,
                    fontSize = 15.sp
                )
                Text(
                    text = email,
                    fontWeight = FontWeight.Bold,
                    color = contentColor,
                    fontSize = 20.sp
                )
                // espaciado
                Text("\n")
                // recuento de likes
                Text(
                    text = "Canciones que te gustan: $likeCount",
                    modifier = Modifier.clickable { navController.navigate(Screen.LikeScreen.route) },
                    color = contentColor,
                    fontSize = 15.sp
                )
                // espaciado
                Text("\n")
                // recuento de dislikes
                Text(
                    text = "Canciones que no te gustan: $dislikeCount",
                    modifier = Modifier.clickable { navController.navigate(Screen.DislikeScreen.route) },
                    color = contentColor,
                    fontSize = 15.sp
                )
            }
        }
    }
}