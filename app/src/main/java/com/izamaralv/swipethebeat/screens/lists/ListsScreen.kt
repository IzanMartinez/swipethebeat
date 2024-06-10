package com.izamaralv.swipethebeat.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.izamaralv.swipethebeat.components.DefaultDivider
import com.izamaralv.swipethebeat.model.DataViewModel
import com.izamaralv.swipethebeat.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun ListsScreen(
    navController: NavController,
    dataViewModel: DataViewModel = viewModel()
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // colors
    val backgroundColor by backgroundColor
    val darkComponentColor by darkComponentColor
    val lightComponentColor by lightComponentColor
    val contentColor by contentColor
    val colorName by colorName

    Column(
        modifier = Modifier
            .background(color = backgroundColor)
            .fillMaxSize()
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

            Column(Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .padding(top = 130.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()

                ) {
                    Text(
                        text = "Tus listas",
                        color = contentColor,
                        fontSize = 50.sp
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(top = 20.dp),
                    color = contentColor
                )

                Row(
                    modifier = Modifier
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Canciones que te han gustado",
                        color = contentColor,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Screen.LikeScreen.route)
                            }
                    )
                }

                DefaultDivider()

                Row(
                    modifier = Modifier
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Canciones que no te han gustado",
                        color = contentColor,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Screen.DislikeScreen.route)
                            }
                    )
                }

                DefaultDivider()
//                Box(
//                    modifier = Modifier
//                        .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
//                        .fillMaxWidth()
//                        .fillMaxHeight(),
//
//                ) {
//                    CustomLogo()
//                }

            }

        }
    }
}