package com.izamaralv.swipethebeat.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.hardComponentColor
import com.izamaralv.swipethebeat.common.softComponentColor
import com.izamaralv.swipethebeat.common.textColor
import com.izamaralv.swipethebeat.exceptions.EmailAlreadyRegisteredException
import com.izamaralv.swipethebeat.exceptions.InvalidEmailException
import com.izamaralv.swipethebeat.exceptions.InvalidPasswordException
import com.izamaralv.swipethebeat.exceptions.PasswordMatchErrorException
import com.izamaralv.swipethebeat.exceptions.RequiredFieldsAreEmptyException
import com.izamaralv.swipethebeat.utils.CustomAuthDialog
import com.izamaralv.swipethebeat.utils.createAccount
import com.izamaralv.swipethebeat.utils.isValidEmail
import com.izamaralv.swipethebeat.utils.isValidPassword
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {

    // initialization
    val coroutineScope = rememberCoroutineScope()


    // variables
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val userPasswordAgain = remember { mutableStateOf("") }
    val isEmailNew = remember { mutableStateOf(false) }
    val isPasswordShown = remember { mutableStateOf(false) }
    val isPasswordAgainShown = remember { mutableStateOf(false) }

    // dialogs
    val requiredFieldsAreEmptyDialog = remember { mutableStateOf(false) }
    val invalidEmailDialog = remember { mutableStateOf(false) }
    val invalidPasswordDialog = remember { mutableStateOf(false) }
    val passwordMatchErrorDialog = remember { mutableStateOf(false) }
    val emailAlreadyRegisteredDialog = remember { mutableStateOf(false) }


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

        Spacer(modifier = Modifier.padding(top = 40.dp))
        // email label
        Text(text = "Introduce tu email", fontSize = 25.sp, color = textColor.copy(alpha = .8f))

        // email text field
        BasicTextField(
            value = userEmail.value,
            onValueChange = { userEmail.value = it },
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
            text = "Introduce tu contraseña",
            fontSize = 25.sp,
            color = textColor.copy(alpha = .8f),
            modifier = Modifier.padding(top = 40.dp)
        )

        Row {
            // password text field
            BasicTextField(
                value = userPassword.value,
                onValueChange = { userPassword.value = it },
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
        // password label
        Text(
            text = "Repite la contraseña",
            fontSize = 25.sp,
            color = textColor.copy(alpha = .8f),
            modifier = Modifier.padding(top = 40.dp)
        )

        // password text field
        BasicTextField(
            value = userPasswordAgain.value,
            onValueChange = { userPasswordAgain.value = it },
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
            .fillMaxHeight()
            .fillMaxWidth(),

        verticalArrangement = Arrangement.Bottom
    ) {


        Button(colors = ButtonDefaults.buttonColors(hardComponentColor),
            modifier = Modifier
                .fillMaxWidth(.5f)
                .height(55.dp)
                .background(color = hardComponentColor, shape = RoundedCornerShape(8.dp)),
            onClick = {
                try {
                    if (userEmail.value.isBlank() || userPassword.value.isBlank())
                        throw RequiredFieldsAreEmptyException()
                    if (!isValidEmail(email = userEmail.value))
                        throw InvalidEmailException()
                    if (!isValidPassword(password = userPassword.value))
                        throw InvalidPasswordException()
                    if (userPassword.value != userPasswordAgain.value)
                        throw PasswordMatchErrorException()
                    coroutineScope.launch {
                        isEmailNew.value = createAccount(
                            email = userEmail.value,
                            password = userPassword.value
                        )
                    }
                    if (!isEmailNew.value)
                        throw EmailAlreadyRegisteredException()
                    Log.d("SignIn", "Signed in correctly")
                } catch (e: RequiredFieldsAreEmptyException) {
                    Log.d("SignIn", "Fields are empty")
                    requiredFieldsAreEmptyDialog.value = true
                } catch (e: InvalidEmailException) {
                    Log.d("SingIn", "Incorrect email")
                    invalidEmailDialog.value = true
                } catch (e: InvalidPasswordException) {
                    Log.d("SingIn", "Incorrect password")
                    invalidPasswordDialog.value = true
                } catch (e: PasswordMatchErrorException) {
                    Log.d("SingIn", "Passwords don't match")
                    passwordMatchErrorDialog.value = true
                } catch (e: EmailAlreadyRegisteredException) {
                    Log.d("createAccount", "Email already registered")
                    emailAlreadyRegisteredDialog.value = true
                }

                navController.navigate("main")

            }) {
            Text(text = "CREAR CUENTA", color = textColor)
        }



        Spacer(modifier = Modifier.height(30.dp))

        ClickableText(
            modifier = Modifier.fillMaxHeight(.1f),
            text = AnnotatedString("Already have an account? Login"),
            onClick = { navController.navigate("login") },
            style = TextStyle(
                textDecoration = TextDecoration.Underline, color = textColor
            )
        )
    }


    // dialog management
    if (requiredFieldsAreEmptyDialog.value) {
        CustomAuthDialog(
            title = "Faltan campos",
            message = "Debes rellenar todos los campos para continuar. "
        ) {
            requiredFieldsAreEmptyDialog.value = false
        }
    }
    if (invalidEmailDialog.value) {
        CustomAuthDialog(
            title = "Email inválido",
            message = "Por favor, introduce un email válido. ",
            variable1 = userEmail,
            variable2 = userPassword,
            variable3 = userPasswordAgain
        ) {
            invalidEmailDialog.value = false
        }
    }
    if (invalidPasswordDialog.value) {
        CustomAuthDialog(
            title = "Contraseña inválida",
            message = "Por favor, introduce una contraseña válida. ",
            variable1 = userPassword,
            variable2 = userPasswordAgain
        ) {
            invalidPasswordDialog.value = false
        }
    }
    if (passwordMatchErrorDialog.value) {
        CustomAuthDialog(
            title = "Error de contraseñas",
            message = "Las contraseñas no coinciden. ",
            variable1 = userPassword,
            variable2 = userPasswordAgain
        ) {
            passwordMatchErrorDialog.value = false
        }
    }
    if (emailAlreadyRegisteredDialog.value) {
        CustomAuthDialog(
            title = "Email existente",
            message = "El email introducido ya esta registrado. ",
            variable1 = userEmail,
            variable2 = userPassword,
            variable3 = userPasswordAgain
        ) {
            emailAlreadyRegisteredDialog.value = false
        }
    }

}




