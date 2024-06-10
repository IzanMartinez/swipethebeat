package com.izamaralv.swipethebeat.screens.credentials

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.colorName
import com.izamaralv.swipethebeat.common.contentColor
import com.izamaralv.swipethebeat.common.darkComponentColor
import com.izamaralv.swipethebeat.common.lightComponentColor
import com.izamaralv.swipethebeat.components.CustomDismissDialog
import com.izamaralv.swipethebeat.components.CustomLogo
import com.izamaralv.swipethebeat.exceptions.InvalidEmailException
import com.izamaralv.swipethebeat.exceptions.InvalidPasswordException
import com.izamaralv.swipethebeat.exceptions.RequiredFieldsAreEmptyException
import com.izamaralv.swipethebeat.model.DataViewModel
import com.izamaralv.swipethebeat.navigation.Screen
import com.izamaralv.swipethebeat.utils.isValidEmail
import com.izamaralv.swipethebeat.utils.isValidPassword
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController, dataViewModel: DataViewModel = viewModel()
) {
    // colors
    val backgroundColor by backgroundColor
    val darkComponentColor by darkComponentColor
    val lightComponentColor by lightComponentColor
    val contentColor by contentColor
    val colorName by colorName


    // initialization
    val coroutineScope = rememberCoroutineScope()


    // variables
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }

    // dialogs
    val requiredFieldsAreEmptyDialog = remember { mutableStateOf(false) }
    val invalidEmailDialog = remember { mutableStateOf(false) }
    val loginFailedDialog = remember { mutableStateOf(false) }

    // background
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // data column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomLogo()
            Spacer(modifier = Modifier.height(50.dp))

            // email label
            Text(
                text = "Introduce tu email",
                fontSize = 25.sp,
                color = contentColor.copy(alpha = .8f)
            )

            // email text field
            BasicTextField(
                value = userEmail.value,
                onValueChange = { userEmail.value = it },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(.8f)
                    .padding(start = 10.dp, top = 30.dp, end = 10.dp)
                    .background(color = lightComponentColor, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 13.dp, horizontal = 15.dp),

                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp, color = contentColor),
            )

            // password label
            Text(
                text = "Introduce tu contraseña",
                fontSize = 25.sp,
                color = contentColor.copy(alpha = .8f),
                modifier = Modifier.padding(top = 40.dp)
            )

            // password text field
            BasicTextField(
                value = userPassword.value,
                onValueChange = { userPassword.value = it },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(.8f)
                    .padding(start = 10.dp, top = 30.dp, end = 10.dp)
                    .background(color = lightComponentColor, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 15.dp, horizontal = 15.dp),
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp, color = contentColor),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(70.dp))

            Button(colors = ButtonDefaults.buttonColors(darkComponentColor),
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(55.dp)
                    .background(color = darkComponentColor, shape = RoundedCornerShape(8.dp)),
                onClick = {
                    try {
                        if (userEmail.value.isBlank() || userPassword.value.isBlank()) throw RequiredFieldsAreEmptyException()

                        if (!isValidEmail(email = userEmail.value)) throw InvalidEmailException()

                        if (!isValidPassword(password = userPassword.value)) throw InvalidPasswordException()

                        coroutineScope.launch {
                            // Attempt to sign in the user
                            val success = dataViewModel.signInWithEmailPassword(
                                email = userEmail.value, password = userPassword.value
                            ) {
                                navController.navigate(Screen.HomeScreen.route)
                            }

//                            try {
//                                if (!success) {
//                                    Log.d("FAILED", "logfailed")
//                                    throw LoginFailedException()
//                                }
//                            } catch (e: Exception) {
//                                Log.d("Login", "Login failed")
//                                loginFailedDialog.value = true
//                            }

                            // Navigate to main screen if login is successful
//                            navController.navigate(Screen.HomeScreen.route)
                            Log.d("Login", "Logged in correctly")
                        }
                    } catch (e: RequiredFieldsAreEmptyException) {
                        Log.d("Login", "Fields are empty")
                        requiredFieldsAreEmptyDialog.value = true
                    } catch (e: InvalidEmailException) {
                        Log.d("Login", "Incorrect email")
                        invalidEmailDialog.value = true
                    } catch (e: Exception) {
                        Log.d("LOGIN", e.toString())
                    }
                }) {
                Text(text = "INICIAR SESIÓN", color = contentColor)
            }



            Spacer(modifier = Modifier.height(50.dp))

            ClickableText(
                modifier = Modifier.fillMaxHeight(.1f),
                text = AnnotatedString("No tienes cuenta? Crea una aquí"),
                onClick = { navController.navigate(Screen.RegisterScreen.route) },
                style = TextStyle(
                    textDecoration = TextDecoration.Underline, color = contentColor
                )
            )

        }


        // dialog management
        if (requiredFieldsAreEmptyDialog.value) {
            CustomDismissDialog(
                title = "Faltan campos",
                message = "Debes rellenar todos los campos para continuar. "
            ) {
                requiredFieldsAreEmptyDialog.value = false
            }
        }
        if (invalidEmailDialog.value) {
            CustomDismissDialog(
                title = "Email inválido",
                message = "Por favor, introduce un email válido. ",
                variable1 = userEmail,
                variable2 = userPassword,
            ) {
                invalidEmailDialog.value = false
            }
        }
        if (loginFailedDialog.value) {
            CustomDismissDialog(
                title = "Credenciales incorrectas",
                message = "No existe ninguna cuenta con esas credenciales",
                variable1 = userPassword
            ) {

            }
        }

    }
}