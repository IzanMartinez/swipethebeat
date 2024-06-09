package com.izamaralv.swipethebeat.exceptions.handler

import android.util.Log
import androidx.compose.runtime.MutableState
import com.izamaralv.swipethebeat.exceptions.EmailAlreadyRegisteredException
import com.izamaralv.swipethebeat.exceptions.InvalidEmailException
import com.izamaralv.swipethebeat.exceptions.InvalidPasswordException
import com.izamaralv.swipethebeat.exceptions.PasswordMatchErrorException
import com.izamaralv.swipethebeat.exceptions.RequiredFieldsAreEmptyException

// Función para manejar excepciones
fun handleException(
    e: Exception,
    requiredFieldsAreEmptyDialog: MutableState<Boolean>,
    invalidEmailDialog: MutableState<Boolean>,
    invalidPasswordDialog: MutableState<Boolean>,
    passwordMatchErrorDialog: MutableState<Boolean>,
    emailAlreadyRegisteredDialog: MutableState<Boolean>
) {
    when (e) {
        is RequiredFieldsAreEmptyException -> requiredFieldsAreEmptyDialog.value = true
        is InvalidEmailException -> invalidEmailDialog.value = true
        is InvalidPasswordException -> invalidPasswordDialog.value = true
        is PasswordMatchErrorException -> passwordMatchErrorDialog.value = true
        is EmailAlreadyRegisteredException -> emailAlreadyRegisteredDialog.value = true
        else -> Log.d("SignIn", "Unexpected error: ${e.message}")
    }
}