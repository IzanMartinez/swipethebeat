package com.izamaralv.swipethebeat.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.hardComponentColor
import com.izamaralv.swipethebeat.common.textColor
import com.izamaralv.swipethebeat.common.softComponentColor
import com.izamaralv.swipethebeat.exceptions.RequiredFieldsAreEmptyException

@Preview
@Composable
fun LoginScreen() {


    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userPasswordAgain by remember { mutableStateOf("") }

    // background
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        // title column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // title box
            Box(
                modifier = Modifier.background(
                        color = Color.White.copy(alpha = .5f), shape = RoundedCornerShape(8.dp)
                    )
//                .fillMaxHeight(.5f)
            ) {
                Text(
                    text = " S W I P E \n\n T H E \n\n B E A T ",
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor,
                    modifier = Modifier.padding(20.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    // data column
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // email label
        Text(text = "Enter your email", fontSize = 25.sp, color = textColor.copy(alpha = .8f))

        // email text field
        BasicTextField(
            value = userEmail,
            onValueChange = { userEmail = it },
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(.8f)
                .padding(start = 10.dp, top = 30.dp, end = 10.dp)
                .background(color = softComponentColor, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 13.dp, horizontal = 15.dp),

            singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
        )


        // password label
        Text(
            text = "Enter your password",
            fontSize = 25.sp,
            color = textColor.copy(alpha = .8f),
            modifier = Modifier.padding(top = 40.dp)
        )

        // password text field
        BasicTextField(
            value = userPassword,
            onValueChange = { userPassword = it },
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(.8f)
                .padding(start = 10.dp, top = 30.dp, end = 10.dp)
                .background(color = softComponentColor, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 15.dp, horizontal = 15.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
            visualTransformation = PasswordVisualTransformation()
        )
    }

    // login column
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight(.86f)
            .fillMaxWidth(),

        verticalArrangement = Arrangement.Bottom
    ) {


        Button(colors = ButtonDefaults.buttonColors(hardComponentColor),
            modifier = Modifier
                .fillMaxWidth(.5f)
                .height(55.dp)
                .background(color = hardComponentColor, shape = RoundedCornerShape(8.dp)),
            onClick = {
                if (userEmail.isBlank() || userPassword.isBlank()) {
                    throw RequiredFieldsAreEmptyException()
                }/* TODO: add ViewModel logic here */
            }) {
            Text("LOGIN")
        }



        Spacer(modifier = Modifier.height(50.dp))

        ClickableText(
            text = AnnotatedString("New here? Create an account"),
            onClick = { /* TODO: add navController*/ },
            style = TextStyle(
                textDecoration = TextDecoration.Underline, color = textColor
            )
        )
    }

}