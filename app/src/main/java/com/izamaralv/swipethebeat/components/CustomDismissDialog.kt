package com.izamaralv.swipethebeat.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.contentColor
import com.izamaralv.swipethebeat.common.darkComponentColor

@Composable
fun CustomDismissDialog(
    title: String,
    message: String,
    variable1: MutableState<String>? = null,
    variable2: MutableState<String>? = null,
    variable3: MutableState<String>? = null,
    onDismissRequest: () -> Unit

) {
    AlertDialog(
        onDismissRequest = {
            // Reset the variables to empty strings before dismissing the dialog
            variable1?.value = ""
            variable2?.value = ""
            variable3?.value = ""
            onDismissRequest()
        },
        title = {
            Text(
                text = title,
                color = contentColor,
                fontSize = 35.sp,
                fontWeight = FontWeight.ExtraBold
            )
        },
        text = {
            Text(text = message, color = contentColor, fontSize = 20.sp)
        },
        confirmButton = {
            Button(
                onClick = { onDismissRequest() }, colors = ButtonDefaults.buttonColors(
                    darkComponentColor
                )
            ) {
                Text(text = "OK", color = Color.Black, fontSize = 15.sp)
            }
        },
        containerColor = backgroundColor,
        dismissButton = null
    )
}

