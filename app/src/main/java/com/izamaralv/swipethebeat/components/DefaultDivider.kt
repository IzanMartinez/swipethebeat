package com.izamaralv.swipethebeat.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.izamaralv.swipethebeat.common.lightComponentColor

@Composable
fun DefaultDivider() {
    val lightComponentColor by lightComponentColor

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        color = lightComponentColor
    )
}