package com.izamaralv.swipethebeat.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.contentColor
import com.izamaralv.swipethebeat.common.darkComponentColor
import com.izamaralv.swipethebeat.components.CustomDrawerSheet
import com.izamaralv.swipethebeat.components.CustomLogoInBox
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController) {

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
                .background(darkComponentColor),
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
                    IconButton(onClick = { } ) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            tint = backgroundColor.copy(alpha = 0f),
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }
        }
    }
}