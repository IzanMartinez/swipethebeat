package com.izamaralv.swipethebeat.screens.lists

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.colorName
import com.izamaralv.swipethebeat.common.contentColor
import com.izamaralv.swipethebeat.common.darkComponentColor
import com.izamaralv.swipethebeat.common.lightComponentColor
import com.izamaralv.swipethebeat.components.CustomDrawerSheet
import com.izamaralv.swipethebeat.components.CustomLogoInBox
import com.izamaralv.swipethebeat.components.DefaultDivider
import com.izamaralv.swipethebeat.data.entities.Song
import com.izamaralv.swipethebeat.model.DataViewModel
import com.izamaralv.swipethebeat.navigation.Screen
import com.izamaralv.swipethebeat.utils.addSongToUserDislikeList
import com.izamaralv.swipethebeat.utils.deleteSongFromUserLikeList
import com.izamaralv.swipethebeat.utils.getEmail
import com.izamaralv.swipethebeat.utils.getLikeListFromUser
import com.izamaralv.swipethebeat.utils.navigateToUrl
import kotlinx.coroutines.launch

@Composable
fun LikeScreen(
    navController: NavController,
    dataViewModel: DataViewModel = viewModel()
) {
    // colors
    val backgroundColor by backgroundColor
    val darkComponentColor by darkComponentColor
    val lightComponentColor by lightComponentColor
    val contentColor by contentColor
    val colorName by colorName

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    var likeList by remember { mutableStateOf<List<Song>>(emptyList()) }
    val email = getEmail()

    LaunchedEffect(email) {
        likeList = email?.let { getLikeListFromUser(it) }!!
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                            onClick = { navController.navigate(Screen.SettingsScreen.route) }) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = null,
                                tint = contentColor,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }
                // list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp)
                ) {
                    items(likeList) { song ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigateToUrl(
                                        context,
                                        song.link
                                    )
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = song.nombre,
                                    color = contentColor,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(start = 20.dp, top = 20.dp)
                                )
                                Text(
                                    text = song.album,
                                    color = contentColor,
                                    modifier = Modifier.padding(start = 20.dp)
                                )
                                Text(
                                    text = song.artista,
                                    color = contentColor,
                                    modifier = Modifier.padding(start = 20.dp)
                                )
                            }
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        if (email != null) {
                                            deleteSongFromUserLikeList(email, song)
                                            addSongToUserDislikeList(email, song)
                                            likeList =
                                                getLikeListFromUser(email)  // Update the likeList state
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(end = 20.dp, top = 20.dp)
                                    .align(alignment = Alignment.CenterVertically)
                            ) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = null,
                                    tint = contentColor,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        DefaultDivider()
                    }
                }
            }
        }
    }
}
