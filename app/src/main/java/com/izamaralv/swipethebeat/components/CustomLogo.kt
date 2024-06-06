package com.izamaralv.swipethebeat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.colorName
import com.izamaralv.swipethebeat.common.contentColor
import com.izamaralv.swipethebeat.common.darkComponentColor
import com.izamaralv.swipethebeat.common.lightComponentColor

@Composable
fun CustomLogo() {
    // colors
    val backgroundColor by backgroundColor
    val darkComponentColor by darkComponentColor
    val lightComponentColor by lightComponentColor
    val contentColor by contentColor
    val colorName by colorName
   Text(
        text = " S T B ",
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.ExtraBold,
        color = contentColor,
        modifier = Modifier.padding(1.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun CustomLogoInBox() {
    Box(
        modifier = Modifier.background(
            color = Color.White.copy(alpha = .5f), shape = RoundedCornerShape(8.dp)
        )
    ) {
        CustomLogo()
    }
}