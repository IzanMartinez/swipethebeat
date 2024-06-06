package com.izamaralv.swipethebeat.common

import androidx.compose.runtime.mutableStateOf
import com.izamaralv.swipethebeat.ui.theme.GreenBackground
import com.izamaralv.swipethebeat.ui.theme.GreenContent
import com.izamaralv.swipethebeat.ui.theme.GreenDarkComponent
import com.izamaralv.swipethebeat.ui.theme.GreenLightComponent
import com.izamaralv.swipethebeat.ui.theme.GreenName

var backgroundColor = mutableStateOf(GreenBackground)
var darkComponentColor = mutableStateOf(GreenDarkComponent)
var lightComponentColor = mutableStateOf(GreenLightComponent)
var contentColor = mutableStateOf(GreenContent)
var colorName = mutableStateOf(GreenName)