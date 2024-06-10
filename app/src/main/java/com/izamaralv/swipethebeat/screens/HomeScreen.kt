package com.izamaralv.swipethebeat.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.izamaralv.swipethebeat.common.Constants
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.colorName
import com.izamaralv.swipethebeat.common.contentColor
import com.izamaralv.swipethebeat.common.darkComponentColor
import com.izamaralv.swipethebeat.common.lightComponentColor
import com.izamaralv.swipethebeat.components.CustomDrawerSheet
import com.izamaralv.swipethebeat.components.CustomLogoInBox
import com.izamaralv.swipethebeat.data.entities.Song
import com.izamaralv.swipethebeat.model.DataViewModel
import com.izamaralv.swipethebeat.navigation.Screen
import com.izamaralv.swipethebeat.utils.addSongToUserDislikeList
import com.izamaralv.swipethebeat.utils.addSongToUserLikeList
import com.izamaralv.swipethebeat.utils.deleteSongFromUserDisplayList
import com.izamaralv.swipethebeat.utils.getDisplayListFromUser
import com.izamaralv.swipethebeat.utils.getEmail
import com.izamaralv.swipethebeat.utils.getSongByDifferentGender
import com.izamaralv.swipethebeat.utils.getSongByGender
import com.izamaralv.swipethebeat.utils.navigateToUrl
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavController, dataViewModel: DataViewModel = viewModel()
) {
    // colors
    val backgroundColor by backgroundColor
    val darkComponentColor by darkComponentColor
    val lightComponentColor by lightComponentColor
    val contentColor by contentColor
    val colorName by colorName

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val email: String? = getEmail()

    var selectedScreen = remember { mutableStateOf(Constants.SCREENS[0]) }

    val displayList = remember { mutableStateOf(listOf<Song>()) }
    val currentSong = remember { mutableStateOf<Song?>(null) }
    val coroutineScope = rememberCoroutineScope()

    var areMoreSongs by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        getEmail()?.let { email ->
            displayList.value = getDisplayListFromUser(email)
            if (displayList.value.isNotEmpty()) {
                currentSong.value = displayList.value.random()
            } else {
                areMoreSongs = false
            }
        }
    }

    fun updateDisplayListLike() {
        coroutineScope.launch {
            if (email != null) {
                displayList.value = getDisplayListFromUser(email)
                if (displayList.value.isNotEmpty()) {
                    currentSong.value = currentSong.value?.let { getSongByGender(email, it.genero) }
                } else {
                    areMoreSongs = false
                }
            }
        }
    }

    fun updateDisplayListDislike() {
        coroutineScope.launch {
            if (email != null) {
                displayList.value = getDisplayListFromUser(email)
                if (displayList.value.isNotEmpty()) {
                    currentSong.value =
                        currentSong.value?.let { getSongByDifferentGender(email, it.genero) }
                } else {
                    areMoreSongs = false
                }
            }
        }
    }

    if (areMoreSongs) {
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
                    .background(backgroundColor), drawerContent = {
                    CustomDrawerSheet(
                        navController = navController,
                        drawerState = drawerState,
                        scope = scope,
                        backgroundColor = darkComponentColor
                    )
                }, drawerState = drawerState
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
                        IconButton(onClick = { navController.navigate(Screen.SettingsScreen.route) }) {
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // imagen del álbum actual
                    Image(
                        painter = rememberAsyncImagePainter(model = currentSong.value?.imagen),
                        modifier = Modifier
                            .size(300.dp)
                            .clickable {
                                currentSong.value?.link?.let {
                                    navigateToUrl(context, it)
                                }
                            },
                        contentDescription = "Portada del álbum"
                    )
                    Text(
                        text = "\n" + currentSong.value?.nombre,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        modifier = Modifier
                            .heightIn(min = 50.dp)
                            .clickable {
                                currentSong.value?.link?.let {
                                    navigateToUrl(context, it)
                                }
                            },
                        maxLines = 2
                    )
                    Text(
                        text = "\n" + currentSong.value?.album,
                        fontSize = 25.sp,
                        color = contentColor,
                        modifier = Modifier.heightIn(min = 50.dp),
                        maxLines = 2
                    )
                    Text(
                        text = "\n" + currentSong.value?.artista,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        modifier = Modifier.heightIn(min = 50.dp),
                        maxLines = 2
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 60.dp, start = 50.dp, end = 50.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // botón si
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    getEmail()?.let { email ->
                                        deleteSongFromUserDisplayList(email, currentSong.value!!)
                                        addSongToUserLikeList(email, currentSong.value!!)
                                    }
                                    if (displayList.value.size > 1) {
                                        updateDisplayListLike()
                                    } else {
                                        areMoreSongs = false
                                    }
                                }
                            }, modifier = Modifier
                                .background(
                                    color = lightComponentColor, shape = RoundedCornerShape(100.dp)
                                )
                                .size(70.dp)
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.Green,
                                modifier = Modifier.size(50.dp)
                            )
                        }

                        // botón no
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    getEmail()?.let { email ->
                                        deleteSongFromUserDisplayList(email, currentSong.value!!)
                                        addSongToUserDislikeList(email, currentSong.value!!)
                                    }
                                    if (displayList.value.size > 1) {
                                        updateDisplayListDislike()
                                    } else {
                                        areMoreSongs = false
                                    }
                                }
                            }, modifier = Modifier
                                .background(
                                    color = lightComponentColor, shape = RoundedCornerShape(100.dp)
                                )
                                .size(70.dp)
                        ) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                }
            }
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) { }
    }
    if (!areMoreSongs) {
        AlertDialog(title = { Text("No hay más canciones") },
            text = { Text("¿Dónde quieres ir?") },
            containerColor = darkComponentColor,
            textContentColor = contentColor,
            iconContentColor = contentColor,
            titleContentColor = contentColor,
            onDismissRequest = { },
            confirmButton = {
                TextButton(
                    onClick = { navController.navigate(Screen.SettingsScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lightComponentColor, contentColor = contentColor
                    )
                ) {
                    Text(text = "Ajustes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { navController.navigate(Screen.ProfileScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lightComponentColor, contentColor = contentColor
                    )
                ) {
                    Text(text = "Perfil")
                }
            })
    }
}







